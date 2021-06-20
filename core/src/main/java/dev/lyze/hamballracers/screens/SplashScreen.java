package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.HamballRacers;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;

public class SplashScreen extends ManagedScreenAdapter {
	private static final Logger<SplashScreen> logger = new Logger<>(SplashScreen.class);

	private final HamballRacers game;
	private final Timer timer;

	private Stage stage;

	public SplashScreen() {
		this.game = (HamballRacers) Gdx.app.getApplicationListener();

		this.timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				game.getScreenManager().pushScreen(MainMenuScreen.class.getName(), BlendingTransition.class.getName());
			}
		}, 3f);

		setupStage();
	}

	private void setupStage() {
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
	public void resize(int width, int height) {
	    stage.getViewport().update(width, height);
	}
}