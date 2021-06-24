package dev.lyze.hamballracers.screens.entities;

import dev.lyze.hamballracers.screens.map.Block;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;

public class HamsterBallMaxSpeed {
    private static final Logger<HamsterBallMaxSpeed> logger = new Logger<>(HamsterBallMaxSpeed.class);
    private static final float vehicleMaxMoveSpeed = 81f; // 61 default

    private final HamsterBall hamsterBall;

    private float maxSpeedMultiplier = 1f;
    private float maxSpeedMultiplierTime = 0f;
    private boolean forceSpeedPenalty = false;

    public HamsterBallMaxSpeed(HamsterBall hamsterBall) {
        this.hamsterBall = hamsterBall;
    }

    public float getMaxMoveSpeed() {
        return vehicleMaxMoveSpeed * maxSpeedMultiplier;
    }

    public float getDefaultMaxMoveSpeed() {
        return vehicleMaxMoveSpeed;
    }

    public void update(float delta) {
        var block = hamsterBall.getLevel().getMap().getBlock(hamsterBall.getX(), hamsterBall.getY());
        var newSpeedMultiplier = block.getSpeedMultiplier();

        maxSpeedMultiplierTime -= delta;

        if (forceSpeedPenalty) {
            if (maxSpeedMultiplier != newSpeedMultiplier && maxSpeedMultiplierTime < 0) {
                setNewSpeedMulitplier(block);
            }
        } else {
            if (newSpeedMultiplier > maxSpeedMultiplier) {
                setNewSpeedMulitplier(block);
            } else if (newSpeedMultiplier < maxSpeedMultiplier && maxSpeedMultiplierTime < 0) {
                setNewSpeedMulitplier(block);
            }
        }
    }

    private void setNewSpeedMulitplier(Block block) {
        maxSpeedMultiplierTime = block.getSpeedMultiplierTime();
        maxSpeedMultiplier = block.getSpeedMultiplier();

        logger.logInfo("Changing speed to " + maxSpeedMultiplier);

        if (block.isForceSpeedMultiplierPenalty())
            forceSpeedPenalty = true;
    }
}
