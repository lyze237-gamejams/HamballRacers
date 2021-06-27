package dev.lyze.hamballracers.screens.level.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.data.LapFinishedEventData;
import dev.lyze.hamballracers.eventSystem.data.LapStartedEventData;
import dev.lyze.hamballracers.eventSystem.events.LapFinishedEvent;
import dev.lyze.hamballracers.eventSystem.events.LapStartedEvent;
import dev.lyze.hamballracers.screens.level.Lap;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.screens.level.map.Block;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.MathUtils2;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class HamsterBall extends Entity {
    private static final Logger<HamsterBall> logger = new Logger<>(HamsterBall.class);

    @Getter
    private static final float vehicleAcceleration = 120f;

    @Getter
    private final Level level;

    @Getter
    private final Vector2 velocity = new Vector2();

    @Getter
    private final Hitbox hitbox;
    @Getter
    private final HamsterBallInput input;
    private final HamsterBallAnimations animations;
    @Getter
    private final HamsterBallMaxSpeed maxSpeed;
    @Getter
    private final Player player;

    @Getter
    private int currentCheckpointNeeded = 0;
    @Getter
    private int currentLap = -1;

    @Getter
    private Lap[] laps;

    public HamsterBall(Level level, Player player) {
        this.level = level;
        this.player = player;

        this.laps = new Lap[level.getLapCount()];

        hitbox = new Hitbox(16, 16, 6, 3.8f, 0, -2f);
        input = new HamsterBallInput(this, player);
        animations = new HamsterBallAnimations(this, player);
        maxSpeed = new HamsterBallMaxSpeed(this);
    }

    @Override
    public void update(float delta) {
        input.update(delta);

        maxSpeed.update(delta);

        calculateVelocity(delta);
        calculateMovement(delta);

        animations.update(delta);

        updateCheckpoints();
    }

    private final Rectangle tempRectangle = new Rectangle();
    private void updateCheckpoints() {
        hitbox.generateRectangle(x, y, tempRectangle);

        var neededCheckpoint = level.getCheckpoints().get(currentCheckpointNeeded);

        if (tempRectangle.overlaps(neededCheckpoint)) {
            logger.logInfo("CHECKPOINT " + currentCheckpointNeeded);
            if (currentCheckpointNeeded == 0) {
                maxSpeed.resetNitro();

                logger.logInfo("0 passed");
                if (currentLap >= laps.length) {
                    logger.logInfo("We're done, no new lap");
                    return;
                }

                if (currentLap >= 0) {
                    logger.logInfo("Finished a proper lap, setting finished time for lap " + currentLap);
                    laps[currentLap].setFinishTime(System.currentTimeMillis());
                    Constants.eventManager.fire(new LapFinishedEvent(new LapFinishedEventData(currentLap, this)));

                    if (currentLap + 1 < laps.length) // don't play complete lap sound when we finish the last one
                        Constants.sounds.getCompleteLap().play();
                }

                currentLap++;

                if (currentLap >= laps.length) {
                    logger.logInfo("Just finished the last lap, we're done " + currentLap);
                    Constants.sounds.getCompleteRace().play();
                    return;
                }

                logger.logInfo("Starting new lap " + currentLap);

                laps[currentLap] = new Lap(currentLap);
                laps[currentLap].setStartTime(System.currentTimeMillis());

                Constants.eventManager.fire(new LapStartedEvent(new LapStartedEventData(currentLap, this)));
            }

            laps[currentLap].getCheckpoints().put(currentCheckpointNeeded++, System.currentTimeMillis());

            if (currentCheckpointNeeded >= level.getCheckpoints().size())
                currentCheckpointNeeded = 0;
        }
    }

    private void calculateVelocity(float delta) {
        var block = level.getMap().getBlock(x, y);

        var accelerationDelta = vehicleAcceleration * delta;
        var decelerationDeltaIce = 6.5f * 5f * delta;

        velocity.x = calculateAxisVelocity(velocity.x, input.getInputVelocity().x, block, accelerationDelta, decelerationDeltaIce);
        velocity.y = calculateAxisVelocity(velocity.y, input.getInputVelocity().y, block, accelerationDelta, decelerationDeltaIce);

        var pythagorasVelocity = ((velocity.x * velocity.x) + (velocity.y * velocity.y));

        float vehicleMaxMoveSpeed = maxSpeed.getMaxMoveSpeed();
        if (pythagorasVelocity > (vehicleMaxMoveSpeed * vehicleMaxMoveSpeed)) {
            float magnitude = (float) Math.sqrt(pythagorasVelocity);
            float multiplier = vehicleMaxMoveSpeed / magnitude;

            velocity.scl(multiplier);
        }
    }

    private float calculateAxisVelocity(float velocity, float inputVelocity, Block block, float accelerationDelta, float decelerationDeltaIce) {
        var icy = block.isIcy();
        var speedMultiplier = block.getSpeedMultiplier();

        if (inputVelocity != 0.0f)
            velocity += inputVelocity * accelerationDelta;
        else
            velocity = MathUtils2.moveTowards(velocity, 0, icy ? decelerationDeltaIce : accelerationDelta);

        var vehicleMaxMoveSpeed = maxSpeed.getMaxMoveSpeed() * speedMultiplier;
        return MathUtils.clamp(velocity, -vehicleMaxMoveSpeed, vehicleMaxMoveSpeed);
    }

    private boolean wasCollision;
    private void calculateMovement(float delta) {
        var moveAmountX = velocity.x * delta;
        var moveAmountY = velocity.y * delta;

        float potentialNewPositionX = x + moveAmountX;
        boolean canMoveX = !(level.getMap().isBlockCollision(potentialNewPositionX, y, hitbox) || level.isHamsterBallCollision(this, potentialNewPositionX, y));

        float potentialNewPositionY = y + moveAmountY;
        boolean canMoveY = !(level.getMap().isBlockCollision(x, potentialNewPositionY, hitbox) || level.isHamsterBallCollision(this, x, potentialNewPositionY));

        if (canMoveX)
            x = potentialNewPositionX;

        var canMoveXAndY = !(level.getMap().isBlockCollision(x, potentialNewPositionY, hitbox) || level.isHamsterBallCollision(this, x, potentialNewPositionY));
        if (canMoveXAndY)
            y = potentialNewPositionY;

        var collidedFromHighSpeed = false;
        if (!canMoveX || !canMoveY) {
            var currVehicleSpeed = velocity.len();

            if (currVehicleSpeed >= maxSpeed.getDefaultMaxMoveSpeed() * 0.75f)
                collidedFromHighSpeed = true;
        }

        var collision = false;
        if (!canMoveX) {
            if (collidedFromHighSpeed) {
                collision = true;
                this.velocity.x *= -0.7f;
            } else {
                this.velocity.x *= 0.7f;
            }
        }

        if (!canMoveY) {
            if (collidedFromHighSpeed) {
                collision = true;
                this.velocity.y *= -0.7f;
            } else {
                this.velocity.y *= 0.7f;
            }
        }

        if (collision && !wasCollision)
            Constants.sounds.getBallbump().play();

        wasCollision = collision;
    }

    @Override
    public void render(SpriteBatch batch, ShapeDrawer drawer) {
        animations.render(batch);
        maxSpeed.render(drawer);
    }

    @Override
    public void debugRender(ShapeDrawer drawer) {
        drawer.setColor(Color.CYAN);
        hitbox.debugDraw(drawer, x, y);
        drawer.circle(x, y, hitbox.getDrawWidth() / 2f);

        drawer.circle(x, y, 1f);
    }
}
