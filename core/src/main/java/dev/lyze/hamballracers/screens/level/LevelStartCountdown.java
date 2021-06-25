package dev.lyze.hamballracers.screens.level;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.data.CountdownTimerFinishedEventData;
import dev.lyze.hamballracers.eventSystem.events.CountdownTimerFinishedEvent;
import dev.lyze.hamballracers.utils.Logger;
import lombok.Getter;

public class LevelStartCountdown extends Container<Label> {
    private final static Logger<LevelStartCountdown> logger = new Logger<>(LevelStartCountdown.class);

    private final Level level;
    private final int countdownTime;

    private boolean started;
    @Getter
    private boolean finished;

    public LevelStartCountdown(Level level, int countdownTime) {
        super(new Label("", Constants.assets.getSkin(), "countdown"));

        this.level = level;
        this.countdownTime = countdownTime;

        setTransform(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!level.getScreen().getGame().getScreenManager().inTransition() && !started) {
            started = true;
            setupActions();
        }
    }

    private void setupActions() {
        logger.logInfo("Starting countdown");
        SequenceAction sequence = Actions.sequence();

        for (int i = countdownTime; i >= 0; i--) {
            int index = i;

            sequence.addAction(Actions.delay(1f));

            sequence.addAction(Actions.run(() -> setOrigin(Align.center)));
            sequence.addAction(Actions.scaleTo(1f, 0.01f, 0.1f));

            if (index > 0)
                sequence.addAction(Actions.run(() -> getActor().setText(String.valueOf(index))));
            else
                sequence.addAction(Actions.run(() -> getActor().setText("GO")));

            sequence.addAction(Actions.run(() -> setOrigin(Align.center)));
            sequence.addAction(Actions.scaleTo(1.25f, 1.25f, 0.1f));
            sequence.addAction(Actions.run(() -> setOrigin(Align.center)));
            sequence.addAction(Actions.scaleTo(1, 1, 0.2f));
            sequence.addAction(Actions.run(() -> setOrigin(Align.center)));
        }

        sequence.addAction(Actions.run(() -> finished = true));
        sequence.addAction(Actions.run(() -> Constants.eventManager.fire(new CountdownTimerFinishedEvent(new CountdownTimerFinishedEventData()))));
        sequence.addAction(Actions.delay(1f));
        sequence.addAction(Actions.fadeOut(2f));

        addAction(sequence);
    }
}
