package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;

public class TransitionScreen extends ManagedScreenAdapter {
    private Timer timer = new Timer();

    @Override
    public void show() {
        super.show();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //game.getScreenManager().pushScreen(CharacterSelectMenu.class.getName(), BlendingTransition.class.getName(), pushParams);
                game.getScreenManager().pushScreen(CharacterSelectMenu.class.getName(), BlendingTransition.class.getName(), pushParams);
            }
        }, 2f);
    }

    @Override
    public Color getClearColor() {
        return Color.WHITE;
    }
}

