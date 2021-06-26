package dev.lyze.hamballracers.screens.level.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.Getter;

public class LevelLapTimer extends Table {
    @Getter
    private final Level level;
    private final HamsterBall[] hamsterBalls;

    private final LevelLapTimerEntry[] levelLapTimers;

    private final Table lapNamesTable = new Table();

    public LevelLapTimer(Level level, HamsterBall[] hamsterBalls) {
        this.level = level;
        this.hamsterBalls = hamsterBalls;

        this.levelLapTimers = new LevelLapTimerEntry[hamsterBalls.length];

        this.defaults().padLeft(6).padRight(6);
        setBackground(new TextureRegionDrawable(Constants.assets.getMainTextureAtlas().getTransparentBackground()));
        setupLapTable();
        add(lapNamesTable);

        for (int i = 0; i < hamsterBalls.length; i++) {
            levelLapTimers[i] = new LevelLapTimerEntry(this, hamsterBalls[i]);
            add(levelLapTimers[i]);
        }
    }

    private void setupLapTable() {
        lapNamesTable.clearChildren();
        lapNamesTable.add(new Label("Lap", Constants.assets.getSkin(), "characterSelectTitle")).row();

        for (int i = 0; i < level.getLapCount(); i++)
            lapNamesTable.add(new Label(String.valueOf(i + 1), Constants.assets.getSkin(), "characterSelectTitle")).row();
    }
}
