package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

    private final Table victoryTable;
    private final Table losersTable;
    private final Table timesTable;

    public VictoryScreen() {
        stage = new Stage(new ExtendViewport(640, 360));

        var bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.add(new Image(Constants.assets.getMainTextureAtlas().getVictoryScreenBb())).grow();
        stage.addActor(bgTable);


        var titleTable = new Table();
        titleTable.setFillParent(true);

        titleTable.add(new Label("Victory!", Constants.assets.getSkin(), "characterSelectTitle")).pad(12).top().expand().row();


        timesTable = new Table();
        timesTable.setFillParent(true);
        victoryTable = new Table();
        victoryTable.setFillParent(true);
        losersTable = new Table();
        losersTable.setFillParent(true);

        stage.addActor(titleTable);
        stage.addActor(timesTable);
        stage.addActor(victoryTable);
        stage.addActor(losersTable);
    }

    private void setupTimesTable() {
        var table = new Table();
        table.defaults().pad(4);

        var laps = sortedHamsterBalls.get(0).getLaps();
        var lapsTable = new Table();
        lapsTable.add(new Label("Lap", Constants.assets.getSkin())).row();
        for (int i = 0; i < laps.length; i++)
            lapsTable.add(new Label(String.valueOf(i + 1), Constants.assets.getSkin())).row();
        lapsTable.add(new Label("Total", Constants.assets.getSkin())).row();

        table.add(lapsTable);

        for (var hamball : sortedHamsterBalls) {
            var hamballTable = new Table();

            var name = new Label("Player " + (hamball.getPlayer().getPlayerIndex() + 1), Constants.assets.getSkin());
            name.setColor(Constants.playerColors[hamball.getPlayer().getPlayerIndex()]);
            hamballTable.add(name).row();

            var sum = 0L;
            for (var lap : hamball.getLaps()) {
                sum += lap.getFinishTime() - lap.getStartTime();
                var elapsed = (lap.getFinishTime() - lap.getStartTime()) / 1000f;
                var seconds = ((int) (elapsed * 1000)) / 1000f;

                hamballTable.add(new Label(String.valueOf(seconds), Constants.assets.getSkin())).row();
            }

            var elapsed = (sum) / 1000f;
            var seconds = ((int) (elapsed * 1000)) / 1000f;

            var total = new Label(String.valueOf(seconds), Constants.assets.getSkin());
            total.setColor(Constants.playerColors[hamball.getPlayer().getPlayerIndex()]);
            hamballTable.add(total).row();

            table.add(hamballTable);
        }

        timesTable.add(table).expand().left().padLeft(24);
    }

    private void setupVictoryTable() {
        victoryTable.add(setupCard(sortedHamsterBalls.get(0), false, 0)).size(192, 192).right().padTop(-32).padRight(24).expand();
    }

    private void setupLosersTable() {
        var subTable = new Table();

        subTable.add().padLeft(24);
        for (int i = 1; i < sortedHamsterBalls.size(); i++)
            subTable.add(setupCard(sortedHamsterBalls.get(i), true, i)).padTop(i * 24).padLeft(4).padRight(4);

        losersTable.add(subTable).expand().bottom().left().padBottom(4);
    }

    private Stack setupCard(HamsterBall winner, boolean tiny, int fontNumber) {
        var stack = new Stack();

        Image bg = new Image(Constants.assets.getMainTextureAtlas().getVictoryFrameBg());
        bg.setColor(Constants.playerColors[winner.getPlayer().getPlayerIndex()]);
        stack.add(bg);
        stack.add(new Image(winner.getPlayer().getCharacter().getPreview()));
        stack.add(new Image(Constants.assets.getMainTextureAtlas().getVictoryFrame()));
        stack.add(new Image(Constants.assets.getMainTextureAtlas().getVictoryFramePlate()));

        var numberTable = new Table();
        numberTable.setFillParent(true);
        Cell<Image> imageCell = numberTable.add(new Image(Constants.assets.getMainTextureAtlas().getVictoryFont().get(fontNumber))).padLeft(tiny ? 6 : 12).padTop(tiny ? -6 : -12).top().left().expand();
        if (!tiny)
            imageCell.size(25 * 2f, 34 * 2f);
        stack.add(numberTable);

        var nameTable = new Table();
        nameTable.add().grow().row();
        nameTable.add(new Label("Player " + (winner.getPlayer().getPlayerIndex() + 1), Constants.assets.getSkin(), tiny ? "default" : "characterSelectTitle")).padBottom(tiny ? 2 : 8).row();

        stack.add(nameTable);
        return stack;
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

        timesTable.clearChildren();
        victoryTable.clearChildren();
        losersTable.clearChildren();

        setupTimesTable();
        setupVictoryTable();
        setupLosersTable();

        Constants.gamepadMapping.addListener(this);
    }

    @Override
    public void hide() {
        Constants.gamepadMapping.removeListener(this);

        Constants.sounds.getUiClick().play();
    }

    /*
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
     */

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
