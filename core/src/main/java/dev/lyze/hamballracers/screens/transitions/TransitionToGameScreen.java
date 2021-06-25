package dev.lyze.hamballracers.screens.transitions;

import com.badlogic.gdx.graphics.Color;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.screens.GameScreen;

public class TransitionToGameScreen extends TransitionScreen {
    @Override
    public void transition() {
        game.getScreenManager().pushScreen(GameScreen.class.getName(), BlendingTransition.class.getName(), pushParams);
    }

    @Override
    public Color getClearColor() {
        return Color.WHITE;
    }
}
