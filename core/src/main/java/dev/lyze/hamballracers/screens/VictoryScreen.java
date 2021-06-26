package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Lap;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import dev.lyze.hamballracers.screens.level.map.Track;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.input.PlayerInputListener;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.var;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VictoryScreen extends ManagedScreenAdapter implements PlayerInputListener {
    private final Stage stage;

    private HamsterBall[] hamsterBalls;
    private List<HamsterBall> sortedHamsterBalls;
    private Track track;
    private long levelStartTime;

    public VictoryScreen() {
        stage = new Stage(new ExtendViewport(640, 360));
    }

    @Override
    public void show() {
        super.show();

        hamsterBalls = (HamsterBall[]) pushParams[0];
        track = (Track) pushParams[1];
        levelStartTime = (long) pushParams[2];

        sortedHamsterBalls = Arrays.stream(hamsterBalls).sorted((o1, o2) ->
                (int) (Arrays.stream(o1.getLaps()).mapToLong(Lap::getFinishTime).sum() - Arrays.stream(o2.getLaps()).mapToLong(Lap::getFinishTime).sum()))
        .collect(Collectors.toList());

        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Victory", Constants.assets.getSkin(), "characterSelectTitle")).pad(12).row();
        root.add(new Image(track.getThumbnail())).size(128, 128).row();
        root.add(setupVictoryTable()).grow().row();

        stage.clear();
        stage.addActor(root);

        Constants.gamepadMapping.addListener(this);
    }

    @Override
    public void hide() {
        Constants.gamepadMapping.removeListener(this);
    }

    private Table setupVictoryTable() {
        var table = new Table();

        var laps = new Table();
        laps.add().size(96, 96).row();
        laps.add(new Label("Lap", Constants.assets.getSkin())).row();
        for (int i = 0; i < sortedHamsterBalls.get(0).getLaps().length; i++)
            laps.add(new Label(String.valueOf(i + 1), Constants.assets.getSkin())).row();

        table.add(laps);

        for (int i = 0; i < sortedHamsterBalls.size(); i++) {
            var hamsterBall = sortedHamsterBalls.get(i);
            var inner = new Table();

            inner.add(new Image(hamsterBall.getPlayer().getCharacter().getPreview())).size(96, 96).row();

            var name = new Label("Player " + (i + 1), Constants.assets.getSkin());
            name.setColor(Constants.playerColors[hamsterBall.getPlayer().getPlayerIndex()]);
            inner.add(name).row();

            for (var lap : hamsterBall.getLaps()) {
                var elapsed = (lap.getFinishTime() - lap.getStartTime()) / 1000f;
                var seconds = ((int) (elapsed * 1000)) / 1000f;

                inner.add(new Label(String.valueOf(seconds), Constants.assets.getSkin())).row();
            }

            table.add(inner);
        }

        return table;
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public Color getClearColor() {
        return Color.TEAL;
    }

    @Override
    public void onDeregistered(VirtualGamepad gamepad, int index, boolean disconnected) {

    }

    @Override
    public void onRegistered(VirtualGamepad gamepad, int index) {

    }

    @Override
    public void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {
        if (button == VirtualGamepadButton.OK && !game.getScreenManager().inTransition())
            game.getScreenManager().pushScreen(MainMenuScreen.class.getName(), BlendingTransition.class.getName());
    }

    @Override
    public void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {

    }
}
