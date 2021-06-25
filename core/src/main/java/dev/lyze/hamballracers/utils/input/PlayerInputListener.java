package dev.lyze.hamballracers.utils.input;

public interface PlayerInputListener {
    void onDeregistered(VirtualGamepad gamepad, int index);
    void onRegistered(VirtualGamepad gamepad, int index);
    void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button, int index);
    void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button, int index);
}
