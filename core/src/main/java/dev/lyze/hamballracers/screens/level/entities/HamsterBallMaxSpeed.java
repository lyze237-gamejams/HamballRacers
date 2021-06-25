package dev.lyze.hamballracers.screens.level.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import dev.lyze.hamballracers.screens.level.map.Block;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.MathUtils2;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

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
                setNewSpeedMultiplier(block, delta);
            }
        } else {
            if (newSpeedMultiplier > speedMultiplier) {
                setNewSpeedMultiplier(block, delta);
            } else if (newSpeedMultiplier < speedMultiplier && speedMultiplierTime < 0) {
                setNewSpeedMultiplier(block, delta);
            }
        }

        if (block.isChargeNitro()) {
            nitroTimeLeft = MathUtils.clamp(nitroTimeLeft += delta, 0, maxNitroTime);
            logger.logInfo("Charging nitro " + nitroTimeLeft);
        }

        usingNitro = false;
        if (hamsterBall.getInput().isUsingNitro()) {
            var velocity = Math.max(Math.abs(hamsterBall.getVelocity().x), Math.abs(hamsterBall.getVelocity().y));

            if (velocity > getDefaultMaxMoveSpeed() || speedMultiplier < 1f)
                nitroTimeLeft -= delta;

            if (nitroTimeLeft > 0)
                usingNitro = true;
        }
    }

    private final Color barRenderColor = new Color();
    private final float[] redHsv = new float[3];
    private final float[] cyanHsv = new float[3];
    public void render(ShapeDrawer drawer) {
        var centerY = hamsterBall.getY() + hamsterBall.getHitbox().getDrawHeight() / 2f;
        var drawWidth = hamsterBall.getHitbox().getDrawWidth();

        Color.RED.toHsv(redHsv);
        Color.CYAN.toHsv(cyanHsv);

        float percent = MathUtils.clamp(nitroTimeLeft / maxNitroTime, 0, 1);

        barRenderColor.fromHsv(MathUtils.lerp(redHsv[0], cyanHsv[0], percent), MathUtils.lerp(redHsv[1], cyanHsv[1], percent), MathUtils.lerp(redHsv[2], cyanHsv[2], percent)).lerp(Color.CYAN, percent);

        drawer.setColor(barRenderColor);
        drawer.filledRectangle(hamsterBall.getX() - drawWidth / 2f,  centerY + 2f, drawWidth * percent, 2f);
        drawer.setColor(Color.BLACK);
        drawer.rectangle(hamsterBall.getX() - drawWidth / 2f,  centerY + 2f, drawWidth, 2f);
    }

    private void setNewSpeedMultiplier(Block block, float delta) {
        speedMultiplierTime = block.getSpeedMultiplierTime();

        var blockSpeedMultiplier = block.getSpeedMultiplier();
        if (blockSpeedMultiplier < this.speedMultiplier)
            this.speedMultiplier = MathUtils2.moveTowards(this.speedMultiplier, blockSpeedMultiplier, 0.5f * delta);
        else
            this.speedMultiplier = blockSpeedMultiplier;

        if (block.isForceSpeedMultiplierPenalty())
            forceSpeedPenalty = true;
    }
}
