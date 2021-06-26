package dev.lyze.hamballracers.screens.level.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.var;

import java.util.HashMap;

public class LevelCheckpointTimerEntry extends Table {
    private final LevelCheckpointTimer levelCheckpointTimer;
    private HamsterBall hamsterBall;

    private final Label name;

    private final HashMap<Integer, Label> checkpointTimers = new HashMap<>();

    public LevelCheckpointTimerEntry(LevelCheckpointTimer levelCheckpointTimer, HamsterBall hamsterBall) {
        this.levelCheckpointTimer = levelCheckpointTimer;
        this.hamsterBall = hamsterBall;

        name = new Label("Player " + hamsterBall.getPlayer().getPlayerIndex() + 1, Constants.assets.getSkin(), "characterSelectTitle");
        name.setColor(Constants.playerColors[hamsterBall.getPlayer().getPlayerIndex()]);
    }

    public void updateCheckpoints() {
        clearChildren();

        add(name).row();
        levelCheckpointTimer.getLevel().getCheckpoints().keySet().stream().sorted().forEach(index -> {
            var cpLabel = new Label("???", Constants.assets.getSkin(), "characterSelectTitle");
            add(cpLabel).row();

            checkpointTimers.put(index, cpLabel);
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        checkpointTimers.forEach((integer, label) -> label.setColor(Color.WHITE));

        if (hamsterBall.getCurrentLap() < 0 || hamsterBall.getCurrentLap() >= hamsterBall.getLaps().length)
            return;

        checkpointTimers.get(hamsterBall.getCurrentCheckpointNeeded()).setColor(Constants.playerColors[hamsterBall.getPlayer().getPlayerIndex()]);

        var lap = hamsterBall.getLaps()[hamsterBall.getCurrentLap()];
        if (lap == null)
            return;

        lap.getCheckpoints().forEach((index, time) ->
        {
            var elapsed = (time - levelCheckpointTimer.getLevel().getLevelStartTime()) / 1000f;
            var seconds = ((int) (elapsed * 1000)) / 1000f;

            checkpointTimers.get(index).setText(String.valueOf(seconds));
        });
    }
}
