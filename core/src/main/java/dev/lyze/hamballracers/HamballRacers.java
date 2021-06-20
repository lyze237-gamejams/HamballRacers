package dev.lyze.hamballracers;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class HamballRacers extends Game {
	@Override
	public void create() {
		setScreen(new FirstScreen());
	}
}