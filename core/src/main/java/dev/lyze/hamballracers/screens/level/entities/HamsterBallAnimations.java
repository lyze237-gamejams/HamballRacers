package dev.lyze.hamballracers.screens.level.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.screens.level.characters.CharacterAnimation;
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

    public HamsterBallAnimations(HamsterBall hamsterBall, Player player) {
        this.hamsterBall = hamsterBall;

        ballAnimation = new Animation<>(0.08f, Constants.assets.getMainTextureAtlas().getHamsterBall(), Animation.PlayMode.LOOP);

        var animations = player.getCharacter().getAnimations();
        playerNormalAnimation = new Animation<>(0.10f, animations.get(CharacterAnimation.NORMAL), Animation.PlayMode.NORMAL);
        playerBlinkAnimation = new Animation<>(0.10f, animations.get(CharacterAnimation.BLINK), Animation.PlayMode.NORMAL);
        playerIdleAnimation = new Animation<>(0.10f, animations.get(CharacterAnimation.IDLE));

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
            float calculatedDelta = delta * Math.max(Math.abs(hamsterBall.getVelocity().x), Math.abs(hamsterBall.getVelocity().y)) / hamsterBall.getMaxSpeed().getDefaultMaxMoveSpeed();

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

    private Color hamsterBallColor = new Color(1, 1, 1, 1);
    public void render(SpriteBatch batch) {
        var ballKeyFrame = ballAnimation.getKeyFrame(ballAnimationDelta);
        var playerKeyFrame = currentPlayerAnimation.getKeyFrame(playerAnimationDelta);

        var drawX = hamsterBall.getX() - hamsterBall.getHitbox().getDrawWidth() / 2f;
        var drawY = this.hamsterBall.getY() - hamsterBall.getHitbox().getDrawHeight() / 2f;

        // player
        if (facingRight)
            batch.draw(playerKeyFrame, drawX, drawY, hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
        else
            batch.draw(playerKeyFrame, drawX + hamsterBall.getHitbox().getDrawWidth(), drawY, -hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());

        // hamball
        /*
        var currentVelocity = Math.max(Math.abs(hamsterBall.getVelocity().x), Math.abs(hamsterBall.getVelocity().y));
        var subtractedCurrentVelocity = MathUtils.clamp(currentVelocity - hamsterBall.getMaxSpeed().getDefaultMaxMoveSpeed(), 0, hamsterBall.getMaxSpeed().getMaxMoveSpeed());
        var dividedVelocity = subtractedCurrentVelocity / (hamsterBall.getMaxSpeed().getDefaultMaxMoveSpeed() * HamsterBallMaxSpeed.getVehicleMaxSpeedMultiplier());
        hamsterBallColor.set(1, 1f - dividedVelocity, 1f - dividedVelocity, 1);
         */
        batch.setColor(Constants.playerColors[hamsterBall.getPlayer().getPlayerIndex()]);
        if (facingRight)
            batch.draw(ballKeyFrame, drawX, drawY, hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
        else
            batch.draw(ballKeyFrame, drawX + hamsterBall.getHitbox().getDrawWidth(), drawY, -hamsterBall.getHitbox().getDrawWidth(), hamsterBall.getHitbox().getDrawHeight());
        batch.setColor(Color.WHITE);
    }
}
