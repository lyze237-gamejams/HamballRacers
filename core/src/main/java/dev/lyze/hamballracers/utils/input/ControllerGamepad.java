package dev.lyze.hamballracers.utils.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import dev.lyze.hamballracers.utils.Logger;

public class ControllerGamepad extends VirtualGamepad {
    private static final Logger<ControllerGamepad> logger = new Logger<>(ControllerGamepad.class);

    private static int incrId;
    private static int id;

    private final Controller controller;
    private final ControllerMapping mapping;

    public ControllerGamepad(String guid, String name, VirtualGamepadListener listener) {
        super(guid, name, listener);

        id = incrId++;

        this.controller = findController(guid);
        this.mapping = controller.getMapping();
    }

    @Override
    public void update(float delta) {
        updateButton(VirtualGamepadButton.LEFT, controller.getButton(mapping.buttonDpadLeft));
        updateButton(VirtualGamepadButton.RIGHT, controller.getButton(mapping.buttonDpadRight));
        updateButton(VirtualGamepadButton.UP, controller.getButton(mapping.buttonDpadUp));
        updateButton(VirtualGamepadButton.DOWN, controller.getButton(mapping.buttonDpadDown));

        updateButton(VirtualGamepadButton.NITRO, controller.getButton(mapping.buttonX));
        updateButton(VirtualGamepadButton.OK, controller.getButton(mapping.buttonA));
    }

    @Override
    public void updateRegisteredState(float delta) {
        if (anyButtonPressed() && !registered) {
            listener.onRegistered(this);
            registered = true;
        }

        if ((controller.getButton(mapping.buttonBack) || !controller.isConnected()) && registered) {
            listener.onDeregistered(this, !controller.isConnected());
            registered = false;
        }
    }

    private Controller findController(String guid) {
        for (Controller controller : Controllers.getControllers()) {
            if (!controller.getUniqueId().equals(guid))
                continue;

            return controller;
        }

        return null;
    }
}
