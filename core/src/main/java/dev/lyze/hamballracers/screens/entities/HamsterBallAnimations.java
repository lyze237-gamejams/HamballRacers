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

    private Animation<TextureAtlas.AtlasRegion> playerNormalAnimation, playerBlinkAnimation;
    private Animation<TextureAtlas.AtlasRegion> currentPlayerAnimation;
    private Animation<TextureAtlas.AtlasRegion> ballAnimation;

    private float animationDelta;
    private boolean facingRight;

    public HamsterBallAnimations(HamsterBall hamsterBall) {
        this.hamsterBall = hamsterBall;

        ballAnimation = new Animation<>(0.08f, Constants.Assets.getMainTextureAtlas().getHamsterBall(), Animation.PlayMode.NORMAL);

        if (hamsterBall.getPlayerIndex() == 0) {
            playerNormalAnimation = new Animation<>(0.08f, Constants.Assets.getMainTextureAtlas().getLyzeNormal(), Animation.PlayMode.NORMAL);
            playerBlinkAnimation = new Animation<>(0.08f, Constants.Assets.getMainTextureAtlas().getLyzeBlink(), Animation.PlayMode.NORMAL);
        }
        else {
            playerNormalAnimation = new Animation<>(0.08f, Constants.Assets.getMainTextureAtlas().getRenbyNormal(), Animation.PlayMode.NORMAL);
            playerBlinkAnimation = new Animation<>(0.08f, Constants.Assets.getMainTextureAtlas().getRenbyBlink(), Animation.PlayMode.NORMAL);
        }

        currentPlayerAnimation = playerNormalAnimation;
    }

    public void update(float delta) {
        if (hamsterBall.getVelocity().x > 0)
            facingRight = true;
        else if (hamsterBall.getVelocity().x < 0)
            facingRight = false;

        if (hamsterBall.getVelocity().x != 0 || hamsterBall.getVelocity().y != 0)
            animationDelta += delta * Math.max(Math.abs(hamsterBall.getVelocity().x), Math.abs(hamsterBall.getVelocity().y)) / HamsterBall.getVehicleMaxMoveSpeed();
    }

    public void render(SpriteBatch batch) {
        var ballKeyFrame = ballAnimation.getKeyFrame(animationDelta);
        var playerKeyFrame = currentPlayerAnimation.getKeyFrame(animationDelta);

        if (ballAnimation.isAnimationFinished(animationDelta)) {
            animationDelta = 0;

            currentPlayerAnimation = random.nextFloat() > blinkPercentage ? playerBlinkAnimation : playerNormalAnimation;

            ballKeyFrame = ballAnimation.getKeyFrame(animationDelta);
            playerKeyFrame = currentPlayerAnimation.getKeyFrame(animationDelta);
        }

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
