package dev.lyze.hamballracers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import dev.lyze.hamballracers.screens.SplashScreen;
import dev.lyze.hamballracers.utils.Logger;

public class HamballRacers extends Game {
	private static final Logger<HamballRacers> logger = new Logger<>(HamballRacers.class);

	@Override
	public void create() {
	    Gdx.app.setLogLevel(Application.LOG_DEBUG);
	    logger.logInfo("Welcome!");

		setScreen(new SplashScreen());
	}
}