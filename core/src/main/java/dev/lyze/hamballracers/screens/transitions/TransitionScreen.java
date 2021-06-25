package dev.lyze.hamballracers.screens.transitions;

import com.badlogic.gdx.utils.Timer;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;

public abstract class TransitionScreen extends ManagedScreenAdapter {
    private final Timer timer = new Timer();

    @Override
    public void show() {
        super.show();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                transition();
            }
        }, 2f);
    }

    public abstract void transition();
}

