package dev.lyze.hamballracers.screens.level;

import lombok.Data;

import java.util.HashMap;

@Data
public class Lap {
    private final int index;

    private long startTime, finishTime;

    private final HashMap<Integer, Long> checkpoints = new HashMap<>();

    public Lap(int index) {
        this.index = index;
    }
}
