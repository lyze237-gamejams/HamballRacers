package dev.lyze.hamballracers.screens.level;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.Getter;

import java.util.Arrays;

public class LevelTimer extends Table {
    @Getter
    private final Level level;
    private final HamsterBall[] hamsterBalls;

    private final LevelTimerEntry[] levelTimers;

    private final Table checkpointNamesTable = new Table();

    private final Label timerLabel;

    public LevelTimer(Level level, HamsterBall[] hamsterBalls) {
        this.level = level;
        this.hamsterBalls = hamsterBalls;

        this.levelTimers = new LevelTimerEntry[hamsterBalls.length];

        timerLabel = new Label("0 seconds", Constants.assets.getSkin(), "characterSelectTitle");
        add(timerLabel).colspan(hamsterBalls.length + 1).padTop(12).row();
        add().padTop(12).row();

        this.defaults().padLeft(6).padRight(6);
        setBackground(new TextureRegionDrawable(Constants.assets.getMainTextureAtlas().getTransparentBackground()));

        setupCheckpointTable();
        add(checkpointNamesTable);

        for (int i = 0; i < hamsterBalls.length; i++) {
            levelTimers[i] = new LevelTimerEntry(this, hamsterBalls[i]);
            add(levelTimers[i]);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (level.getLevelStartTime() > 0) {
            timerLabel.setText((int) level.getLevelElapsedTime() + " seconds");
        }
    }

    private void setupCheckpointTable() {
        checkpointNamesTable.clearChildren();
        checkpointNamesTable.add(new Label("CP", Constants.assets.getSkin(), "characterSelectTitle")).row();

        level.getCheckpoints().keySet().stream().sorted().forEach(index ->
                checkpointNamesTable.add(new Label(String.valueOf(index + 1), Constants.assets.getSkin(), "characterSelectTitle")).row());
    }

    public void updateCheckpoints() {
        setupCheckpointTable();
        Arrays.stream(levelTimers).forEach(LevelTimerEntry::updateCheckpoints);
    }
}
