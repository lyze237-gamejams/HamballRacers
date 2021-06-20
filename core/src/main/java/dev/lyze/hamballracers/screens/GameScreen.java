package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;

public class GameScreen extends ManagedScreenAdapter {
	private final Stage stage;

	public GameScreen() {
		stage = new Stage(new FitViewport(1920, 1080));
		stage.setDebugAll(Constants.DEBUG);
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