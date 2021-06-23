package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dev.lyze.hamballracers.screens.map.Block;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.utils.MathUtils2;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class HamsterBall extends Entity {
    private final Vector2 velocity = new Vector2();

    private static final float vehicleAcceleration = 120f;
    private static final float vehicleMaxMoveSpeed = 81f; // 61 default
    private final int playerIndex;

    private final int[][] playerControls = new int[][] {
            new int[] { Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D },
            new int[] { Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT }
    };

    private Animation<Texture> runAnimation;
    private float animationDelta;

    private final Hitbox hitbox;

    private boolean facingRight;

    public HamsterBall(Map map, float x, float y, int playerIndex) {
        super(map, x, y);

        this.playerIndex = playerIndex;

        hitbox = new Hitbox(16, 16, 6, 3.8f, 0, -2f);

        setupAnimation();
    }

    private void setupAnimation() {
        var runAnimationTextures = new Array<Texture>();
        for (int i = 1; i <= 8; i++)
            runAnimationTextures.add(new Texture(Gdx.files.internal("player/ball/lyzeball" + i + ".png")));
        runAnimation = new Animation<>(0.08f, runAnimationTextures, Animation.PlayMode.LOOP);
    }

    @Override
    public void update(float delta) {
        var inputVelocity = readInputVelocity();

        calculateVelocity(inputVelocity, delta);
        calculateMovement(delta);

        updateAnimation(delta);
    }

    private void updateAnimation(float delta) {
        if (velocity.x > 0)
            facingRight = true;
        else if (velocity.x < 0)
            facingRight = false;

        if (velocity.x != 0 || velocity.y != 0)
            animationDelta += delta * Math.max(Math.abs(velocity.x), Math.abs(velocity.y)) / vehicleMaxMoveSpeed;
    }

    private Vector2 readInputVelocity() {
        var inputVelocity = new Vector2();

        if (Gdx.input.isKeyPressed(playerControls[playerIndex][0]))
            inputVelocity.y = 1;
        if (Gdx.input.isKeyPressed(playerControls[playerIndex][1]))
            inputVelocity.y = -1;

        if (Gdx.input.isKeyPressed(playerControls[playerIndex][2]))
            inputVelocity.x = -1;
        if (Gdx.input.isKeyPressed(playerControls[playerIndex][3]))
            inputVelocity.x = 1;

        return inputVelocity;
    }

    private void calculateVelocity(Vector2 inputVelocity, float delta) {
        var block = map.getBlock(x, y);
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
        boolean canMoveX = !map.isBlockCollision(potentialNewPositionX, y, hitbox);

        float potentialNewPositionY = y + moveAmountY;
        boolean canMoveY = !map.isBlockCollision(x, potentialNewPositionY, hitbox);

        if (canMoveX)
            x = potentialNewPositionX;

        var canMoveXAndY = !map.isBlockCollision(x, potentialNewPositionY, hitbox);
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
        var keyFrame = runAnimation.getKeyFrame(animationDelta);

        var drawX = this.x - hitbox.getDrawWidth() / 2f;
        var drawY = this.y - hitbox.getDrawHeight() / 2f;

        if (facingRight)
            batch.draw(keyFrame, drawX, drawY, hitbox.getDrawWidth(), hitbox.getDrawHeight());
        else
            batch.draw(keyFrame, drawX + hitbox.getDrawWidth(), drawY, -hitbox.getDrawWidth(), hitbox.getDrawHeight());
    }

    @Override
    public void debugRender(ShapeDrawer drawer) {
        drawer.setColor(Color.CYAN);
        hitbox.debugDraw(drawer, x, y);
        drawer.circle(x, y, hitbox.getDrawWidth() / 2f);

        drawer.circle(x, y, 1f);
    }
}