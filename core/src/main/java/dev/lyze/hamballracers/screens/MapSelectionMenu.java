package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

public class MapSelectionMenu extends ManagedScreenAdapter implements PlayerInputListener {
    private static final Logger<MapSelectionMenu> logger = new Logger<>(MapSelectionMenu.class);

    private static final int mapsPerRow = 3;

    private final Stage stage;

    private final MapSelectionMenuEntry[] tracks = new MapSelectionMenuEntry[Constants.tracks.length];

    private Player[] players;

    private int selectedIndex;

    private Image selectedMap;
    private Label selectedMapLabel;

    public MapSelectionMenu() {
        stage = new Stage(new ExtendViewport(640, 360));

        var bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(new Image(Constants.assets.getMainTextureAtlas().getMapSelectBg())).grow();
        stage.addActor(bgTable);

        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Pick the map", Constants.assets.getSkin(), "characterSelectTitle")).pad(12).row();
        root.add(setupMapStuff()).grow().row();
        stage.addActor(root);

        var buttons = new Table();
        buttons.setFillParent(true);
        buttons.add(new Image(Constants.assets.getMainTextureAtlas().getButtonsSelectOnly())).expand().right().bottom().pad(2);
        stage.addActor(buttons);
    }

    private Table setupMapStuff() {
        var table = new Table();

        var bigMapStack = new Stack();
        var bigMapTable = new Table();

        bigMapStack.add(new Image(Constants.assets.getMainTextureAtlas().getFrameMapBig()));
        bigMapStack.add(selectedMap = new Image(Constants.tracks[0].getThumbnail()));
        bigMapStack.add(new Image(Constants.assets.getMainTextureAtlas().getPlateMapBig()));

        bigMapTable.add().growY().row();
        bigMapTable.add(selectedMapLabel = new Label("Desert", Constants.assets.getSkin(), "characterSelectTitle")).padBottom(17);

        bigMapStack.add(bigMapTable);

        table.add(bigMapStack).size(256, 256).left().padLeft(24).expand();

        table.add(setupMapSelection()).right().padRight(24).expand();

        return table;
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

        Constants.gamepadMapping.addListener(this);
    }

    @Override
    public void hide() {
        Constants.gamepadMapping.removeListener(this);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        stage.act();
        stage.draw();
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

            Constants.sounds.getUiClick().play();

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

        selectedMapLabel.setText(tracks[selectedIndex].getTrack().getName());
        selectedMap.addAction(Actions.sequence(
                Actions.fadeOut(0.1f),
                Actions.run(() -> selectedMap.setDrawable(new TextureRegionDrawable(tracks[selectedIndex].getTrack().getThumbnail()))),
                Actions.fadeIn(0.1f)
        ));

        Constants.sounds.getUiClick().play();
    }

    @Override
    public void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {

    }
}
