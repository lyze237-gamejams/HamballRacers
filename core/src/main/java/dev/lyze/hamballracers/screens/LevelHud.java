package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import lombok.var;

public class LevelHud extends Stage {
    private final Level level;

    private LevelStartCountdown countdown;

    public LevelHud(Level level) {
        super(new FitViewport(1600, 900));
        this.level = level;

        var countdownTable = new Table();
        countdownTable.setFillParent(true);

        countdown = new LevelStartCountdown(level, 3);
        countdownTable.add(countdown).padTop(100).top().expand();

        addActor(countdownTable);
    }
}
