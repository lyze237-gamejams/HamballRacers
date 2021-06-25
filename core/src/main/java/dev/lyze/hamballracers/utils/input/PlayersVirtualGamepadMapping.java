package dev.lyze.hamballracers.utils.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayersVirtualGamepadMapping implements VirtualGamepadListener {
    private static final Logger<PlayersVirtualGamepadMapping> logger = new Logger<>(PlayersVirtualGamepadMapping.class);

    private final KeyboardGamepad wasd = new KeyboardGamepad("keyboard0", this, false);
    private final KeyboardGamepad arrows = new KeyboardGamepad("keyboard1", this, true);

    private final HashMap<String, VirtualGamepad> connectedGamepads = new HashMap<>();
    private final VirtualGamepad[] playerGamepads = new VirtualGamepad[Constants.maxPlayers];

    private final ArrayList<PlayerInputListener> listeners = new ArrayList<>();

    public PlayersVirtualGamepadMapping() {
        connectVirtualGamepad(wasd.guid);
        connectVirtualGamepad(arrows.guid);

        for (int i = 0; i < Controllers.getControllers().size; i++) {
            connectVirtualGamepad(Controllers.getControllers().get(i));
        }

        Controllers.addListener(new ControllerAdapter() {
            @Override
            public void connected(Controller controller) {
                connectVirtualGamepad(controller);
            }

            @Override
            public void disconnected(Controller controller) {
                disconnectVirtualGamepad(controller.getUniqueId());
            }
        });
    }

    public void update(float delta) {
        for (VirtualGamepad gamepad : connectedGamepads.values()) {
            gamepad.update(delta);
            gamepad.updateRegisteredState(delta);
        }
    }

    public void addListener(PlayerInputListener listener) {
        listeners.add(listener);
    }

    private void connectVirtualGamepad(Controller controller) {
        logger.logInfo("Controller connected " + controller.getUniqueId());

        var gamepad = new ControllerGamepad(controller.getUniqueId(), controller.getName(), this);
        connectedGamepads.put(gamepad.getGuid(), gamepad);
    }

    private void connectVirtualGamepad(String guid) {
        logger.logInfo("Controller connected " + guid);

        VirtualGamepad gamepad;

        if (guid.equals(wasd.guid))
            gamepad = wasd;
        else if (guid.equals(arrows.guid))
            gamepad = arrows;
        else
            throw new IllegalArgumentException("Wrong method call. use connectVirtualGamepad(Controller) for controllers.");

        connectedGamepads.put(guid, gamepad);
    }

    private void disconnectVirtualGamepad(String guid) {
        logger.logInfo("Controller disconnected, removing instance " + guid);
        var removedGamepad = connectedGamepads.remove(guid);
        onDeregistered(removedGamepad);
    }

    @Override
    public void onDeregistered(VirtualGamepad gamepad) {
        for (int i = 0; i < playerGamepads.length; i++) {
            if (playerGamepads[i] != null && playerGamepads[i].getGuid().equals(gamepad.getGuid())) {
                logger.logInfo("Removing player " + i + " gamepad " + gamepad);

                for (PlayerInputListener l : listeners)
                    l.onDeregistered(gamepad, i);

                playerGamepads[i] = null;
                return;
            }
        }
    }

    @Override
    public void onRegistered(VirtualGamepad gamepad) {
        for (int i = 0; i < playerGamepads.length; i++) {
            if (playerGamepads[i] == null) {
                logger.logInfo("Assigning player " + i + " gamepad " + gamepad);

                for (PlayerInputListener l : listeners)
                    l.onRegistered(gamepad, i);
                playerGamepads[i] = gamepad;

                return;
            }
        }
    }

    @Override
    public void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button) {
        for (int i = 0; i < playerGamepads.length; i++) {
            if (playerGamepads[i] != null && playerGamepads[i].getGuid().equals(gamepad.getGuid())) {
                for (PlayerInputListener l : listeners)
                    l.onButtonDown(gamepad, button, i);
                playerGamepads[i] = gamepad;

                return;
            }
        }
    }

    @Override
    public void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button) {
        for (int i = 0; i < playerGamepads.length; i++) {
            if (playerGamepads[i] != null && playerGamepads[i].getGuid().equals(gamepad.getGuid())) {
                for (PlayerInputListener l : listeners)
                    l.onButtonUp(gamepad, button, i);
                playerGamepads[i] = gamepad;

                return;
            }
        }
    }
}
