package dev.lyze.hamballracers.screens.level;

import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.Getter;
import lombok.var;

public class Player {
    @Getter
    private final Character character;
    @Getter
    private final int playerIndex;

    public Player(Character character, int playerIndex) {
        this.character = character;
        this.playerIndex = playerIndex;
    }

    public boolean isButtonPressed(VirtualGamepadButton button) {
        var gamepad = Constants.gamepadMapping.getGamepad(playerIndex);
        if (gamepad == null)
            return false;

        return gamepad.isButtonPressed(button);
    }
}
