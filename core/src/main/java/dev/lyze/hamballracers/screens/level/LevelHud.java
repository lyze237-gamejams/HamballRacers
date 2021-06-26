package dev.lyze.hamballracers.screens.level;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.Getter;
import lombok.var;

public class LevelHud extends Stage {
    private final Level level;

    @Getter
    private LevelStartCountdown countdown;

    @Getter
    private LevelTimer timer;

    public LevelHud(Level level, HamsterBall[] hamsterBalls) {
        super(new FitViewport(1600, 900));
        this.level = level;

        var countdownTable = new Table();
        countdownTable.setFillParent(true);

        countdown = new LevelStartCountdown(level, 3);
        countdownTable.add(countdown).padTop(100).top().expand();

        var timerTable = new Table();
        timerTable.setFillParent(true);

        timer = new LevelTimer(level, hamsterBalls);
        timerTable.add(timer).top().left().pad(12).expand();

        addActor(countdownTable);
        addActor(timerTable);
    }
}
