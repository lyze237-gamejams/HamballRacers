package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.hamballracers.screens.Level;
import dev.lyze.hamballracers.screens.map.Block;
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
    private static final float vehicleMaxMoveSpeed = 81f; // 61 default

    private final Level level;

    @Getter
    private final int playerIndex;

    @Getter
    private final Vector2 velocity = new Vector2();

    @Getter
    private final Hitbox hitbox;
    private final HamsterBallInput input;
    private final HamsterBallAnimations animations;

    public HamsterBall(Level level, int playerIndex) {
        this.level = level;
        this.playerIndex = playerIndex;

        hitbox = new Hitbox(16, 16, 6, 3.8f, 0, -2f);
        input = new HamsterBallInput(this);
        animations = new HamsterBallAnimations(this);
    }

    @Override
    public void update(float delta) {
        var inputVelocity = input.readInputVelocity();

        calculateVelocity(inputVelocity, delta);
        calculateMovement(delta);

        animations.update(delta);
    }

    private void calculateVelocity(Vector2 inputVelocity, float delta) {
        var block = level.getMap().getBlock(x, y);
        var speedMultiplier = block.getSpeedMultiplier();

        var accelerationDelta = vehicleAcceleration * delta;
        var decelerationDeltaIce = 6.5f * 5f * delta;

        velocity.x = calculateAxisVelocity(velocity.x, inputVelocity.x, block, accelerationDelta, decelerationDeltaIce);
        velocity.y = calculateAxisVelocity(velocity.y, inputVelocity.y, block, accelerationDelta, decelerationDeltaIce);

        var pythagorasVelocity = ((velocity.x * velocity.x) + (velocity.y * velocity.y));

        float vehicleMaxMoveSpeed = HamsterBall.vehicleMaxMoveSpeed * speedMultiplier;
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

        var vehicleMaxMoveSpeed = HamsterBall.vehicleMaxMoveSpeed * speedMultiplier;
        return MathUtils.clamp(velocity, -vehicleMaxMoveSpeed, vehicleMaxMoveSpeed);
    }

    private void calculateMovement(float delta) {
        var moveAmountX = velocity.x * delta;
        var moveAmountY = velocity.y * delta;

        float potentialNewPositionX = x + moveAmountX;
        boolean canMoveX = !(level.getMap().isBlockCollision(potentialNewPositionX, y, hitbox) || level.isHamsterBallCollision(playerIndex, potentialNewPositionX, y));

        float potentialNewPositionY = y + moveAmountY;
        boolean canMoveY = !(level.getMap().isBlockCollision(x, potentialNewPositionY, hitbox) || level.isHamsterBallCollision(playerIndex, x, potentialNewPositionY));

        if (canMoveX)
            x = potentialNewPositionX;

        var canMoveXAndY = !(level.getMap().isBlockCollision(x, potentialNewPositionY, hitbox) || level.isHamsterBallCollision(playerIndex, x, potentialNewPositionY));
        if (canMoveXAndY)
            y = potentialNewPositionY;

        var collidedFromHighSpeed = false;
        if (!canMoveX || !canMoveY) {
            var currVehicleSpeed = velocity.len();

            if (currVehicleSpeed >= vehicleMaxMoveSpeed * 0.75f)
                collidedFromHighSpeed = true;
        }

        if (!canMoveX)
            this.velocity.x *= collidedFromHighSpeed ? -0.7f : 0.7f;

        if (!canMoveY) {
            if (collidedFromHighSpeed) {
                this.velocity.y *= -0.7f;
            } else {
                this.velocity.y *= 0.7f;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        animations.render(batch);
    }

    @Override
    public void debugRender(ShapeDrawer drawer) {
        drawer.setColor(Color.CYAN);
        hitbox.debugDraw(drawer, x, y);
        drawer.circle(x, y, hitbox.getDrawWidth() / 2f);

        drawer.circle(x, y, 1f);
    }
}
