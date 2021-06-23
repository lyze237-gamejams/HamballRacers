package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;

public class MainMenuScreen extends ManagedScreenAdapter {
    private final Stage stage;

    private Music themeSong;

    public MainMenuScreen() {
        stage = new Stage(new FitViewport(1920, 1080));
        stage.setDebugAll(Constants.Debug);
    }

    @Override
    public void show() {
        super.show();

        game.getScreenManager().pushScreen(GameScreen.class.getName(), BlendingTransition.class.getName());
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
