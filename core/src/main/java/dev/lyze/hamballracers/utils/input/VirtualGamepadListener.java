package dev.lyze.hamballracers.utils.input;

public interface VirtualGamepadListener {
    void onDeregistered(VirtualGamepad gamepad);
    void onRegistered(VirtualGamepad gamepad);
    void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button);
    void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button);
}
