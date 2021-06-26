package dev.lyze.hamballracers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import de.eskalon.commons.screen.transition.impl.PushTransition;
import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import dev.lyze.hamballracers.screens.*;
import dev.lyze.hamballracers.screens.transitions.TransitionToGameScreen;
import dev.lyze.hamballracers.utils.Logger;

import java.util.Arrays;

public class HamballRacers extends ManagedGame<ManagedScreen, ScreenTransition> {
    private static final Logger<HamballRacers> logger = new Logger<>(HamballRacers.class);

    private SpriteBatch batch;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        logger.logInfo("Welcome!");

        logger.logInfo("Catching keys");
        Arrays.asList(Input.Keys.SPACE, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT).forEach(key -> Gdx.input.setCatchKey(key, true));

        logger.logInfo("Setting up everything");
        super.create();

        this.batch = new SpriteBatch();

        logger.logInfo("Loading all assets");
        Constants.initialize();

        logger.logInfo("Setting up screens");
        setupScreens();
        logger.logInfo("Setting up transitions");
        setupTransitions();

        if (Constants.debug)
            this.screenManager.pushScreen(GameScreen.class.getName(), BlendingTransition.class.getName());
        else
            this.screenManager.pushScreen(SplashScreen.class.getName(), BlendingTransition.class.getName());
    }

    private void setupScreens() {
        this.screenManager.addScreen(SplashScreen.class.getName(), new SplashScreen());
        this.screenManager.addScreen(MainMenuScreen.class.getName(), new MainMenuScreen());
        this.screenManager.addScreen(GameScreen.class.getName(), new GameScreen());
        this.screenManager.addScreen(TransitionToGameScreen.class.getName(), new TransitionToGameScreen());
        this.screenManager.addScreen(CharacterSelectMenu.class.getName(), new CharacterSelectMenu());
        this.screenManager.addScreen(MapSelectionMenu.class.getName(), new MapSelectionMenu());
        this.screenManager.addScreen(VictoryScreen.class.getName(), new VictoryScreen());
    }

    private void setupTransitions() {
        this.screenManager.addScreenTransition(BlendingTransition.class.getName(), new BlendingTransition(batch, 1f));
        this.screenManager.addScreenTransition(HorizontalSlicingTransition.class.getName(), new HorizontalSlicingTransition(batch, 11, 0.75f));
        this.screenManager.addScreenTransition(PushTransition.class.getName(), new PushTransition(batch, SlidingDirection.LEFT, 1.25f));
    }

    @Override
    public void render() {
        Constants.gamepadMapping.update(Gdx.graphics.getDeltaTime());

        super.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.F9))
            Constants.debug = !Constants.debug;
    }

    @Override
    public void resize(int width, int height) {
        if (width == 0 || height == 0)
            return;

        super.resize(width, height);

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }
}
