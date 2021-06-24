package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.hamballracers.Constants;
import lombok.var;

import java.util.Random;

public class HamsterBallAnimations {
    private static final Random random = new Random();
    private static final float blinkPercentage = 0.9f;

    private final HamsterBall hamsterBall;

    private final Animation<TextureAtlas.AtlasRegion> ballAnimation;
    private final Animation<TextureAtlas.AtlasRegion> playerNormalAnimation, playerBlinkAnimation, playerIdleAnimation;

    private Animation<TextureAtlas.AtlasRegion> currentPlayerAnimation;

    private float ballAnimationDelta, playerAnimationDelta;
    private boolean facingRight;

    public HamsterBallAnimations(HamsterBall hamsterBall) {
        this.hamsterBall = hamsterBall;

        ballAnimation = new Animation<>(0.08f, Constants.Assets.getMainTextureAtlas().getHamsterBall(), Animation.PlayMode.LOOP);

        if (hamsterBall.getPlayerIndex() == 0) {
            playerNormalAnimation = new Animation<>(0.10f, Constants.Assets.getMainTextureAtlas().getLyzeNormal(), Animation.PlayMode.NORMAL);
            playerBlinkAnimation = new Animation<>(0.10f, Constants.Assets.getMainTextureAtlas().getLyzeBlink(), Animation.PlayMode.NORMAL);
            playerIdleAnimation = new Animation<>(0.10f, Constants.Assets.getMainTextureAtlas().getLyzeIdle());
        }
        else {
            playerNormalAnimation = new Animation<>(0.10f, Constants.Assets.getMainTextureAtlas().getRenbyNormal(), Animation.PlayMode.NORMAL);
            playerBlinkAnimation = new Animation<>(0.10f, Constants.Assets.getMainTextureAtlas().getRenbyBlink(), Animation.PlayMode.NORMAL);
            playerIdleAnimation = new Animation<>(0.10f, Constants.Assets.getMainTextureAtlas().getRenbyIdle());
        }
        playerIdleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        currentPlayerAnimation = playerNormalAnimation;
    }

    public void update(float delta) {
        if (hamsterBall.getVelocity().x > 0)
            facingRight = true;
        else if (hamsterBall.getVelocity().x < 0)
            facingRight = false;

        if (hamsterBall.getVelocity().x == 0 && hamsterBall.getVelocity().y == 0) {
            currentPlayerAnimation = playerIdleAnimation;
            playerAnimationDelta = 0f;
        } else {
            float calculatedDelta = delta * Math.max(Math.abs(hamsterBall.getVelocity().x), Math.abs(hamsterBall.getVelocity().y)) / HamsterBall.getVehicleMaxMoveSpeed();

            if (currentPlayerAnimation == playerIdleAnimation)
                currentPlayerAnimation = playerNormalAnimation;

            playerAnimationDelta += calculatedDelta;
            ballAnimationDelta += calculatedDelta;
        }

        if (playerNormalAnimation.isAnimationFinished(playerAnimationDelta)) {
            playerAnimationDelta = 0;

            currentPlayerAnimation = random.nextFloat() > blinkPercentage ? playerBlinkAnimation : playerNormalAnimation;
        }
    }

    public void render(SpriteBatch batch) {
        var ballKeyFrame = ballAnimation.getKeyFrame(ballAnimationDelta);
        var playerKeyFrame = currentPlayerAnimation.getKeyFrame(playerAnimationDelta);

        var drawX = hamsterBall.getX() - hamsterBall.getHitbox().getDrawWidth() / 2f;
        var drawY = this.hamsterBall.getY() - hamsterBall.getHitbox().getDrawHeight() / 2f;

        if (facingRight) {
            batch.draw(playerKeyFrame, drawX, drawY, hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
            batch.draw(ballKeyFrame, drawX, drawY, hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
        } else {
            batch.draw(playerKeyFrame, drawX + hamsterBall.getHitbox().getDrawWidth(), drawY, -hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
            batch.draw(ballKeyFrame, drawX + hamsterBall.getHitbox().getDrawWidth(), drawY, -hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
        }
    }
}
