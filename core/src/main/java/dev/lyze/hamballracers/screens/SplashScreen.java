package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import lombok.var;

public class SplashScreen extends ManagedScreenAdapter {
    private final Stage stage;

    private Timer.Task timerTask;

    public SplashScreen() {
        stage = new Stage(new FitViewport(1920, 1080));
        setupStage();
    }

    @Override
    public void show() {
        super.show();

        timerTask = new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                game.getScreenManager().pushScreen(MainMenuScreen.class.getName(), BlendingTransition.class.getName());
            }
        }, 3f);
    }

    private void setupStage() {
        stage.setDebugAll(Constants.Debug);

        var root = new Table();
        root.setFillParent(true);
        root.add(new Image(Constants.Assets.getMainTextureAtlas().getLogo())).width(500).height(500);

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        stage.getViewport().apply();

        if ((Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) && timerTask.isScheduled()) {
            timerTask.cancel();
            game.getScreenManager().pushScreen(MainMenuScreen.class.getName(), null);
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
