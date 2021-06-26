package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.screens.level.map.Track;
import dev.lyze.hamballracers.screens.transitions.TransitionToGameScreen;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.input.PlayerInputListener;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.var;

import java.util.Arrays;
import java.util.Objects;

public class MapSelectionMenu extends ManagedScreenAdapter implements PlayerInputListener {
    private static final Logger<MapSelectionMenu> logger = new Logger<>(MapSelectionMenu.class);

    private static final int mapsPerRow = 3;

    private final Stage stage;

    private final MapSelectionMenuEntry[] tracks = new MapSelectionMenuEntry[Constants.tracks.length];

    private Player[] players;

    private int selectedIndex;

    public MapSelectionMenu() {
        stage = new Stage(new ExtendViewport(640, 360));

        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Map Selection", Constants.assets.getSkin(), "characterSelectTitle")).pad(12).row();
        root.add(setupMapSelection()).grow().row();
        stage.addActor(root);
    }

    private Table setupMapSelection() {
        var table = new Table();
        table.defaults().pad(2);

        for (int i = 0; i < Constants.tracks.length; i++) {
            Track track = Constants.tracks[i];

            var entry = new MapSelectionMenuEntry(track, i);
            tracks[i] = entry;

            table.add(entry);

            if (i % (mapsPerRow - 1) == 0 && i > 0)
                table.row();
        }

        changeFocus(selectedIndex, 0);

        return table;
    }

    @Override
    public void show() {
        super.show();

        players = (Player[]) pushParams;

        var playerOrder = Arrays.stream(players).filter(Objects::nonNull).mapToInt(Player::getPlayerIndex).toArray();
        var fullOrder = new IntArray(Constants.maxPlayers);

        for (int o : playerOrder)
            fullOrder.add(o);

        for (int i = 0; i < Constants.maxPlayers; i++)
            if (!fullOrder.contains(i))
                fullOrder.add(i);

        var shrink = fullOrder.shrink();

        Constants.gamepadMapping.setReconnectOrder(shrink);

        Constants.gamepadMapping.addListener(this);
    }

    @Override
    public void hide() {
        Constants.gamepadMapping.removeListener(this);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public Color getClearColor() {
        return Color.TEAL;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void onDeregistered(VirtualGamepad gamepad, int index, boolean disconnected) { }

    @Override
    public void onRegistered(VirtualGamepad gamepad, int index) { }

    @Override
    public void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {
        if (button == VirtualGamepadButton.LEFT)
            changeFocus(selectedIndex - 1, index);
        else if (button == VirtualGamepadButton.RIGHT)
            changeFocus(selectedIndex + 1, index);
        else if (button == VirtualGamepadButton.DOWN)
            changeFocus(selectedIndex + mapsPerRow, index);
        else if (button == VirtualGamepadButton.UP)
            changeFocus(selectedIndex - mapsPerRow, index);
        else if (button == VirtualGamepadButton.OK) {
            if (game.getScreenManager().inTransition())
                return;

            game.getScreenManager().pushScreen(TransitionToGameScreen.class.getName(), BlendingTransition.class.getName(), players, tracks[selectedIndex].getTrack());
        }
    }

    private void changeFocus(int newIndex, int playerIndex) {
        tracks[selectedIndex].unsetFocus();

        if (newIndex < 0)
            newIndex = 0;
        else if (newIndex >= tracks.length)
            newIndex = tracks.length - 1;

        tracks[selectedIndex = newIndex].setFocus(playerIndex);
    }

    @Override
    public void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {

    }
}
