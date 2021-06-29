package dev.lyze.hamballracers.utils.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardGamepad extends VirtualGamepad {
    private final boolean useArrowKeys;

    public KeyboardGamepad(String guid, VirtualGamepadListener listener, boolean useArrowKeys) {
        super(guid, "KB " + (useArrowKeys ? "Arrows" : "WASD"), listener);

        this.useArrowKeys = useArrowKeys;
    }

    @Override
    public void update(float delta) {
        updateButton(VirtualGamepadButton.LEFT, Gdx.input.isKeyPressed(useArrowKeys ? Input.Keys.LEFT : Input.Keys.A));
        updateButton(VirtualGamepadButton.RIGHT, Gdx.input.isKeyPressed(useArrowKeys ? Input.Keys.RIGHT : Input.Keys.D));
        updateButton(VirtualGamepadButton.DOWN, Gdx.input.isKeyPressed(useArrowKeys ? Input.Keys.DOWN : Input.Keys.S));
        updateButton(VirtualGamepadButton.UP, Gdx.input.isKeyPressed(useArrowKeys ? Input.Keys.UP : Input.Keys.W));

        updateButton(VirtualGamepadButton.NITRO, Gdx.input.isKeyPressed(useArrowKeys ? Input.Keys.SHIFT_RIGHT: Input.Keys.SHIFT_LEFT));

        if (useArrowKeys)
            updateButton(VirtualGamepadButton.OK, Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) || Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT));
        else
            updateButton(VirtualGamepadButton.OK, Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT));
    }

    @Override
    public void updateRegisteredState(float delta) {
        if (anyButtonPressed() && !registered) {
            listener.onRegistered(this);
            registered = true;
        }

        if (Gdx.input.isKeyJustPressed(useArrowKeys ? Input.Keys.ENTER: Input.Keys.ESCAPE) && registered) {
            listener.onDeregistered(this, false);
            registered = false;
        }
    }
}
