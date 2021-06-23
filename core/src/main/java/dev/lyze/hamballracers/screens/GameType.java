package dev.lyze.hamballracers.screens;

import lombok.Getter;

public enum GameType {

    TIME_TRIAL(1), PVP(2);

    @Getter
    private final int playerCount;

    GameType(int playerCount) {
        this.playerCount = playerCount;
    }
}
