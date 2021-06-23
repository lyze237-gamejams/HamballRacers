package dev.lyze.hamballracers.utils.ui;

import com.badlogic.gdx.audio.Music;
import dev.lyze.hamballracers.utils.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.var;

import java.util.ArrayList;

public class MusicActionMap {
    private static final Logger<MusicActionMap> logger = new Logger<>(MusicActionMap.class);

    private final Music music;
    private final ArrayList<MusicActionEntry> entries;

    private int currentIndex;

    public MusicActionMap(Music music, ArrayList<MusicActionEntry> entries) {
        this.music = music;
        this.entries = entries;

        this.entries.sort((o1, o2) -> Float.compare(o1.getTime(), o2.getTime()));
    }

    public void start(float volume) {
        logger.logInfo("Starting action map with volume " + volume);
        music.setVolume(volume);
        music.play();
    }

    public void update() {
        if (currentIndex >= entries.size() || !music.isPlaying())
            return;

        var action = entries.get(currentIndex);
        float position = music.getPosition();
        if (position > action.getTime()) {
            logger.logInfo("Executing action " + action + " at " + position);
            action.getAction().run();
            currentIndex++;
        }
    }

    public void finish() {
        while (currentIndex < entries.size())
            entries.get(currentIndex++).getAction().run();
    }

    @Data
    @AllArgsConstructor
    public static class MusicActionEntry {
        private float time;
        private Runnable action;
    }
}
