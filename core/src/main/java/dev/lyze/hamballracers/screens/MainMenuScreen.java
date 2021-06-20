package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.hamballracers.HamballRacers;
import dev.lyze.hamballracers.utils.Logger;

public class MainMenuScreen extends ManagedScreen {
	private static final Logger<MainMenuScreen> logger = new Logger<>(MainMenuScreen.class);

	private final HamballRacers game;

	private Stage stage;

	public MainMenuScreen() {
		this.game = (HamballRacers) Gdx.app.getApplicationListener();
	}

	@Override
	protected void create() {
	    stage = new Stage(new FitViewport(1920, 1080));
	}

	@Override
	public void render(float delta) {
	    stage.getViewport().apply();

	    stage.act();
	    stage.draw();
	}

	@Override
	public void hide() {

	}

	@Override
	public void resize(int width, int height) {
	    stage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {

	}
}