package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.math.MathUtils;
import dev.lyze.hamballracers.screens.map.Block;
import dev.lyze.hamballracers.utils.Logger;
import lombok.Getter;
import lombok.var;

public class HamsterBallMaxSpeed {
    private static final Logger<HamsterBallMaxSpeed> logger = new Logger<>(HamsterBallMaxSpeed.class);
    private static final float vehicleMaxMoveSpeed = 81f; // 61 default
    @Getter
    private static final float vehicleMaxSpeedMultiplier = 3f;
    private static final float maxNitroTime = 2f;
    private static final float nitroSpeedBoost = 3f;

    private final HamsterBall hamsterBall;

    @Getter
    private float speedMultiplier = 1f;
    private float speedMultiplierTime = 0f;
    private boolean forceSpeedPenalty = false;

    private float nitroTimeLeft = maxNitroTime;
    @Getter
    private boolean usingNitro;

    public HamsterBallMaxSpeed(HamsterBall hamsterBall) {
        this.hamsterBall = hamsterBall;
    }

    public float getMaxMoveSpeed() {
        return vehicleMaxMoveSpeed * speedMultiplier * (usingNitro ? nitroSpeedBoost : 1);
    }

    public float getDefaultMaxMoveSpeed() {
        return vehicleMaxMoveSpeed;
    }

    public void update(float delta) {
        var block = hamsterBall.getLevel().getMap().getBlock(hamsterBall.getX(), hamsterBall.getY());
        var newSpeedMultiplier = block.getSpeedMultiplier();

        speedMultiplierTime -= delta;

        if (forceSpeedPenalty) {
            if (speedMultiplier != newSpeedMultiplier && speedMultiplierTime < 0) {
                setNewSpeedMulitplier(block);
            }
        } else {
            if (newSpeedMultiplier > speedMultiplier) {
                setNewSpeedMulitplier(block);
            } else if (newSpeedMultiplier < speedMultiplier && speedMultiplierTime < 0) {
                setNewSpeedMulitplier(block);
            }
        }

        if (block.isChargeNitro()) {
            nitroTimeLeft = MathUtils.clamp(nitroTimeLeft += delta, 0, maxNitroTime);
            logger.logInfo("Charging nitro " + nitroTimeLeft);
        }

        usingNitro = hamsterBall.getInput().isUsingNitro() && (nitroTimeLeft -= delta) > 0;
        if (usingNitro)
            logger.logInfo("Nitro: " + nitroTimeLeft);
    }

    private void setNewSpeedMulitplier(Block block) {
        speedMultiplierTime = block.getSpeedMultiplierTime();
        speedMultiplier = block.getSpeedMultiplier();

        logger.logInfo("Changing speed to " + speedMultiplier);

        if (block.isForceSpeedMultiplierPenalty())
            forceSpeedPenalty = true;
    }
}
