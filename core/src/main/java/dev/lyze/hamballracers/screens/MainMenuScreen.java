package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.eskalon.commons.screen.transition.impl.PushTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.ui.MusicActionMap;
import lombok.var;

import java.util.ArrayList;

public class MainMenuScreen extends ManagedScreenAdapter {
    private static final Logger<MainMenuScreen> logger = new Logger<>(MainMenuScreen.class);

    private final Stage stage;

    private final ArrayList<MusicActionMap.MusicActionEntry> musicActionMapEntries = new ArrayList<>();
    private final MusicActionMap musicActionMap;

    public MainMenuScreen() {
        stage = new Stage(new ExtendViewport(640, 360));

        setupBackground();
        setupButtons();

        musicActionMap = new MusicActionMap(Constants.sounds.getThemeSong(), musicActionMapEntries);
    }

    private void setupButtons() {
        var table = new Table();
        table.setFillParent(true);

        Image buttons = new Image(Constants.assets.getMainTextureAtlas().getMainMenuButtons());
        table.add(buttons).left().bottom().padLeft(24).padBottom(60).expand();

        buttons.addAction(Actions.fadeOut(0));
        musicActionMapEntries.add(new MusicActionMap.MusicActionEntry(5.9f, () -> buttons.addAction(Actions.fadeIn(0.4f))));

        stage.addActor(table);
    }

    private void setupBackground() {
        var layers = Constants.assets.getMainTextureAtlas().getMainMenuBg();

        var table = new Table();
        table.setFillParent(true);

        var stack = new Stack();

        float[] backgroundTimings = new float[]{ 1f, 2f, 2.7f, 5.9f };

        for (int i = 0; i < layers.size; i++) {
            var layer = layers.get(i);

            var image = new Image(layer);
            stack.addActor(image);

            image.addAction(Actions.fadeOut(0));
            musicActionMapEntries.add(new MusicActionMap.MusicActionEntry(backgroundTimings[i], () -> image.addAction(Actions.fadeIn(0.4f))));
        }

        var innerTable = new Table();
        innerTable.setFillParent(true);

        var title = new Label("Super Hamsterball Racers", Constants.assets.getSkin(), "title");
        var subTitle = new Label("Made by @Lyze237 @RegaloRenby @jakebutineau @Borazilla", Constants.assets.getSkin());

        innerTable.add(title).top().padTop(4).expand().row();
        innerTable.add(subTitle).bottom().padBottom(2).expand().row();

        title.addAction(Actions.fadeOut(0));
        subTitle.addAction(Actions.fadeOut(0));

        musicActionMapEntries.add(new MusicActionMap.MusicActionEntry(4.4f, () -> {
            title.addAction(Actions.fadeIn(0.4f));
            subTitle.addAction(Actions.fadeIn(0.4f));
        }));

        stack.add(innerTable);
        table.add(stack).grow();

        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();

        musicActionMap.start();
    }

    @Override
    public void render(float delta) {
        musicActionMap.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            skipCutScene();
        }

        stage.getViewport().apply();

        stage.act();
        stage.draw();
    }

    private void skipCutScene() {
        if (!musicActionMap.isFinished()) {
            musicActionMap.finish();
        } else if (!game.getScreenManager().inTransition()) {
            game.getScreenManager().pushScreen(CharacterSelectMenu.class.getName(), PushTransition.class.getName());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
