package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.utils.MathUtils2;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends Entity {
    private final Vector2 velocity = new Vector2();

    private static final float vehicleAcceleration = 120f;
    private static final float vehicleMaxMoveSpeed = 61f;

    public Player(Map map, float x, float y, float width, float height) {
        super(map, x, y, width, height);
    }

    private boolean isOnIce;

    @Override
    public void update(float delta) {
        this.isOnIce = map.getBlock(x, y).isSlippery();

        var inputVelocity = readInputVelocity();

        calculateVelocity(inputVelocity, isOnIce, delta);
        calculateMovement(delta);
    }

    private Vector2 readInputVelocity() {
        var inputVelocity = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            inputVelocity.y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            inputVelocity.y = -1;

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            inputVelocity.x = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            inputVelocity.x = 1;

        return inputVelocity;
    }

    private void calculateVelocity(Vector2 inputVelocity, boolean isOnIce, float delta) {
        var accelerationDelta = vehicleAcceleration * delta;
        var decelerationDeltaIce = 6.5f * 5f * delta;

        if (inputVelocity.x != 0.0f)
            velocity.x += inputVelocity.x * accelerationDelta;
        else
            velocity.x = MathUtils2.moveTowards(velocity.x, 0, isOnIce ? decelerationDeltaIce : accelerationDelta);

        if (inputVelocity.y != 0)
            velocity.y += inputVelocity.y * accelerationDelta;
        else
            velocity.y = MathUtils2.moveTowards(velocity.y, 0, isOnIce ? decelerationDeltaIce : accelerationDelta);

        var maxVehicleMoveSpeed = 61f; // todo ???

        velocity.x = MathUtils.clamp(velocity.x, -maxVehicleMoveSpeed, maxVehicleMoveSpeed);
        velocity.y = MathUtils.clamp(velocity.y, -maxVehicleMoveSpeed, maxVehicleMoveSpeed);

        var pythagorasVelocity = ((velocity.x * velocity.x) + (velocity.y * velocity.y));

        if (pythagorasVelocity > (maxVehicleMoveSpeed * maxVehicleMoveSpeed)) {
            float magnitude = (float) Math.sqrt(pythagorasVelocity);
            float multiplier = maxVehicleMoveSpeed / magnitude;

            velocity.scl(multiplier);
        }
    }

    private void calculateMovement(float delta) {
        var moveAmountX = velocity.x * delta;
        var moveAmountY = velocity.y * delta;

        float newPositionX = x + moveAmountX;
        boolean canMoveX = !map.getBlock(newPositionX, y).isCollision(); //currentLevel.CheckPlayerPositionIsValidWalkSpace(new PointFloat2D(newPositionX, startingPositionY));

        float newPositionY = y + moveAmountY;
        boolean canMoveY = !map.getBlock(x, newPositionY).isCollision(); //currentLevel.CheckPlayerPositionIsValidWalkSpace(new PointFloat2D(startingPositionX, newPositionY));

        var newPosition = new Vector2(x, y);

        if (canMoveX)
            newPosition.x = newPositionX;

        var canMoveXAndY = !map.getBlock(newPosition.x, newPositionY).isCollision(); //currentLevel.CheckPlayerPositionIsValidWalkSpace(new PointFloat2D(newPosition.x, newPositionY));
        if (canMoveXAndY)
            newPosition.y = newPositionY;

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

        x = newPosition.x;
        y = newPosition.y;
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void debugRender(ShapeDrawer drawer) {
        drawer.setColor(isOnIce ? Color.BLUE : Color.CYAN);
        drawer.rectangle(x - width / 2f, y - width / 2f, width, height);
        drawer.circle(x, y, width / 2f);

        drawer.circle(x, y, 1f);
    }
}
