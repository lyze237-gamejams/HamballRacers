package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.hamballracers.HamballRacers;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;

public class SplashScreen extends ManagedScreen {
	private static final Logger<SplashScreen> logger = new Logger<>(SplashScreen.class);

	private final HamballRacers game;

	private Stage stage;

	public SplashScreen() {
		this.game = (HamballRacers) Gdx.app.getApplicationListener();
	}

	@Override
	protected void create() {
	    stage = new Stage(new FitViewport(1920, 1080));

		var root = new Table();
		root.setFillParent(true);
		root.add(new Image(new Texture(Gdx.files.internal("logo.png")))).width(500).height(500);

	    stage.addActor(root);
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