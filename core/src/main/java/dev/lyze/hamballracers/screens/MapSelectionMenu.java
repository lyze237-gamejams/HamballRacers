package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.screens.level.map.Track;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import lombok.var;

public class MapSelectionMenu extends ManagedScreenAdapter {
    private final Stage stage;

    private Player[] players;
    private MapSelectionMenuEntry[] tracks = new MapSelectionMenuEntry[Constants.tracks.length];

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

            if (i % 3 == 0 && i > 0)
                table.row();
        }

        return table;
    }

    @Override
    public void show() {
        super.show();

        players = (Player[]) pushParams;

        // game.getScreenManager().pushScreen(TransitionToGameScreen.class.getName(), BlendingTransition.class.getName(), (Object[]) gamePlayers);
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
}
