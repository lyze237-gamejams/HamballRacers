package dev.lyze.hamballracers.utils.input;

import lombok.Getter;
import lombok.var;

import java.util.HashMap;

public abstract class VirtualGamepad {
    @Getter
    private String name;
    @Getter
    protected String guid;
    @Getter
    protected final VirtualGamepadListener listener;
    @Getter
    protected boolean registered;

    private final HashMap<VirtualGamepadButton, Boolean> buttonsPressed = new HashMap<>();

    public VirtualGamepad(String guid, String name, VirtualGamepadListener listener) {
        this.guid = guid;
        this.name = name;
        this.listener = listener;
    }

    public void updateButton(VirtualGamepadButton button, boolean state) {
        var oldState  = buttonsPressed.getOrDefault(button, false);

        if (!oldState && state)
            listener.onButtonDown(this, button);
        if (oldState && !state)
            listener.onButtonUp(this, button);

        buttonsPressed.put(button, state);
    }

    public boolean anyButtonPressed() {
        return buttonsPressed.values().stream().anyMatch(b -> b);
    }

    public abstract void update(float delta);
    public abstract void updateRegisteredState(float delta);

    @Override
    public String toString() {
        return "VirtualGamepad{" +
                "guid='" + guid + '\'' +
                '}';
    }
}
