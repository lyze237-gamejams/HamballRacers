package dev.lyze.hamballracers.screens.level.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.Getter;
import lombok.var;

public class LevelHud extends Stage {
    private final Level level;

    @Getter
    private LevelStartCountdown countdown;

    @Getter
    private LevelCheckpointTimer checkpointTimer;

    @Getter
    private LevelLapTimer lapTimer;

    public LevelHud(Level level, HamsterBall[] hamsterBalls) {
        super(new FitViewport(1600, 900));
        this.level = level;

        var countdownTable = new Table();
        countdownTable.setFillParent(true);

        countdown = new LevelStartCountdown(level, 3);
        countdownTable.add(countdown).padTop(100).top().expand();

        var checkpointTimerTable = new Table();
        checkpointTimerTable.setFillParent(true);

        checkpointTimer = new LevelCheckpointTimer(level, hamsterBalls);
        checkpointTimerTable.add(checkpointTimer).top().left().pad(12).expand();

        var lapTimerTable = new Table();
        lapTimerTable.setFillParent(true);

        lapTimer = new LevelLapTimer(level, hamsterBalls);
        lapTimerTable.add(lapTimer).bottom().left().pad(12).expand();

        addActor(checkpointTimerTable);
        addActor(countdownTable);
        addActor(lapTimerTable);
    }
}
