package dev.lyze.hamballracers.screens.level.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.EventListener;
import dev.lyze.hamballracers.eventSystem.events.LapFinishedEvent;
import dev.lyze.hamballracers.eventSystem.events.LapStartedEvent;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.var;

import java.util.HashMap;

public class LevelLapTimerEntry extends Table {
    private final LevelLapTimer timer;
    private final HamsterBall hamsterBall;

    private final Label name;
    private final HashMap<Integer, Label> lapTimers = new HashMap<>();

    public LevelLapTimerEntry(LevelLapTimer timer, HamsterBall hamsterBall) {
        this.timer = timer;
        this.hamsterBall = hamsterBall;

        name = new Label("Player " + (hamsterBall.getPlayer().getPlayerIndex() + 1), Constants.assets.getSkin(), "characterSelectTitle");
        name.setColor(Constants.playerColors[hamsterBall.getPlayer().getPlayerIndex()]);

        setupLaps();
        setupEventListeners();
    }

    public void setupLaps() {
        add(name).row();
        for (int i = 0; i < timer.getLevel().getLapCount(); i++) {
            var label = new Label("???", Constants.assets.getSkin(), "characterSelectTitle");
            add(label).row();

            lapTimers.put(i, label);
        }
    }

    private void setupEventListeners() {
        Constants.eventManager.addListener(new EventListener<LapFinishedEvent>(LapFinishedEvent.class, timer.getLevel()) {
            @Override
            protected void fire(LapFinishedEvent event) {
                lapTimers.forEach((index, label) -> {
                    if (event.getData().getIndex() == index) {
                        var oldLap = hamsterBall.getLaps()[index];
                        var elapsed = (oldLap.getFinishTime() - oldLap.getStartTime()) / 1000f;
                        var seconds = ((int) (elapsed * 1000)) / 1000f;

                        label.setText(String.valueOf(seconds));
                    }
                });
            }

            @Override
            protected boolean shouldFire(LapFinishedEvent event) {
                return hamsterBall == event.getData().getHamsterBall();
            }
        });

        Constants.eventManager.addListener(new EventListener<LapStartedEvent>(LapStartedEvent.class, timer.getLevel()) {
            @Override
            protected void fire(LapStartedEvent event) {
                lapTimers.forEach((index, label) -> {
                    if (event.getData().getIndex() == index) {
                        if (index == event.getData().getIndex()) {
                            label.setColor(Constants.playerColors[hamsterBall.getPlayer().getPlayerIndex()]);
                        } else {
                            label.setColor(Color.WHITE);
                        }
                    }
                });
            }

            @Override
            protected boolean shouldFire(LapStartedEvent event) {
                return hamsterBall == event.getData().getHamsterBall();
            }
        });
    }
}
