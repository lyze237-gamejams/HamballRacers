package dev.lyze.hamballracers.screens.level;

import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.Getter;
import lombok.Setter;

public class Player {
    @Getter @Setter
    private VirtualGamepad gamepad;
    @Getter
    private Character character;
    @Getter
    private int playerIndex;

    public Player(VirtualGamepad gamepad, Character character, int playerIndex) {
        this.gamepad = gamepad;
        this.character = character;
        this.playerIndex = playerIndex;
    }

    public boolean isButtonPressed(VirtualGamepadButton button) {
        if (gamepad == null)
            return false;

        return gamepad.isButtonPressed(button);
    }
}
