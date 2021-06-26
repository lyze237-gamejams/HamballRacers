package dev.lyze.hamballracers.screens.level.entities;

import com.badlogic.gdx.math.Vector2;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.Getter;

public class HamsterBallInput {
    private final HamsterBall hamsterBall;

    @Getter
    private final Vector2 inputVelocity = new Vector2();
    private final Player player;

    public HamsterBallInput(HamsterBall hamsterBall, Player player) {
        this.hamsterBall = hamsterBall;
        this.player = player;
    }

    public void update(float delta) {
        readInputVelocity();
    }

    public void readInputVelocity() {
        inputVelocity.set(0, 0);

        if (hamsterBall.getCurrentLap() >= hamsterBall.getLaps().length)
            return;

        if (player.isButtonPressed(VirtualGamepadButton.UP))
            inputVelocity.y = 1;
        if (player.isButtonPressed(VirtualGamepadButton.DOWN))
            inputVelocity.y = -1;

        if (player.isButtonPressed(VirtualGamepadButton.LEFT))
            inputVelocity.x = -1;
        if (player.isButtonPressed(VirtualGamepadButton.RIGHT))
            inputVelocity.x = 1;
    }

    public boolean isUsingNitro() {
        return player.isButtonPressed(VirtualGamepadButton.NITRO);
    }
}
