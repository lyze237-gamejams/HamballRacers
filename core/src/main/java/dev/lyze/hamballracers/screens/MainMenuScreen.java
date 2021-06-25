package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.ui.MusicActionMap;
import lombok.var;

import java.util.ArrayList;

public class MainMenuScreen extends ManagedScreenAdapter {
    private static final Logger<MainMenuScreen> logger = new Logger<>(MainMenuScreen.class);

    private final Stage sky = new Stage(new FillViewport(240, 135));
    private final Stage text = new Stage(new ExtendViewport(1920, 1080));

    private final ArrayList<MusicActionMap.MusicActionEntry> musicActionMapEntries = new ArrayList<>();
    private final MusicActionMap musicActionMap;
    private boolean finishable, wantsToFinish;

    public MainMenuScreen() {
        setupBackground();
        setupTitle();
        setupButtons();
        musicActionMap = new MusicActionMap(Constants.assets.getThemeSong(), musicActionMapEntries);
    }

    private void setupBackground() {
        var layers = Constants.assets.getMainTextureAtlas().getSky();

        float[] backgroundTimings = new float[] {1f, 1.78f, 2.29f};

        if (layers.size != backgroundTimings.length)
            logger.logFatal("Sky size != background timings length");

        for (int i = 0; i < layers.size; i++) {
            var layer = layers.get(i);

            Image image = new Image(new Sprite(layer));
            sky.addActor(image);

            image.addAction(Actions.fadeOut(0));
            musicActionMapEntries.add(new MusicActionMap.MusicActionEntry(backgroundTimings[i], () -> image.addAction(Actions.fadeIn(0.4f))));
        }
    }

    private void setupTitle() {
        var titleTable = new Table();
        titleTable.setFillParent(true);

        titleTable.add(new Label("SUPER HAMSTERBALL RACERS", Constants.assets.getSkin(), "title"))
                .padTop(140).padLeft(140).left().top().expand();
        text.addActor(titleTable);

        titleTable.addAction(Actions.moveBy(-2000, 0, 0));
        musicActionMapEntries.add(new MusicActionMap.MusicActionEntry(3.5f, () -> titleTable.addAction(Actions.moveBy(2000, 0, 0.75f, Interpolation.exp5))));
    }

    private void setupButtons() {
        var menuTable = new Table();
        menuTable.setFillParent(true);

        var menuSubTable = new Table();

        addButton(menuSubTable, "Time trial", 5.3f, () -> game.getScreenManager().pushScreen(TransitionScreen.class.getName(), BlendingTransition.class.getName(), GameType.TIME_TRIAL));
        addButton(menuSubTable, "Local Multiplayer", 6.3f, () -> game.getScreenManager().pushScreen(TransitionScreen.class.getName(), HorizontalSlicingTransition.class.getName(), GameType.PVP));
        addButton(menuSubTable, "Exit", 6.6f, () -> Gdx.app.exit());

        menuTable.add(menuSubTable).right().padRight(100).padBottom(100).expand();
        text.addActor(menuTable);

        menuTable.addAction(Actions.sequence(
                Actions.moveBy(1000, 0, 0),
                Actions.delay(1f),
                Actions.moveBy(-1000, 0, 0),
                Actions.run(() -> finishable = true)));
    }

    private TextButton addButton(Table table, String name, float delay, Runnable onClick) {
        var button = new TextButton(name, Constants.assets.getSkin());
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClick.run();
            }
        });
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.moveBy(20, 0, 0.2f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.moveBy(-20, 0, 0.2f));
            }
        });
        table.add(button).right().row();

        button.addAction(Actions.sequence(Actions.delay(0.25f), Actions.moveBy(2000, 0, 0)));
        musicActionMapEntries.add(new MusicActionMap.MusicActionEntry(delay, () -> button.addAction(Actions.moveBy(-2000, 0, 0.75f, Interpolation.exp5Out))));

        return button;
    }

    @Override
    public void show() {
        super.show();

        musicActionMap.start(0.5f);

        addInputProcessor(text);
    }

    @Override
    public void render(float delta) {
        musicActionMap.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            wantsToFinish = true;

        if (finishable && wantsToFinish)
            musicActionMap.finish();

        sky.getViewport().apply();
        sky.act();
        sky.draw();

        text.getViewport().apply();
        text.act();
        text.draw();
    }

    @Override
    public void resize(int width, int height) {
        sky.getViewport().update(width, height);
        text.getViewport().update(width, height, true);
    }

    private final Color clearColor = Color.valueOf("#0BA5DD");
    @Override
    public Color getClearColor() {
        return clearColor;
    }
}
