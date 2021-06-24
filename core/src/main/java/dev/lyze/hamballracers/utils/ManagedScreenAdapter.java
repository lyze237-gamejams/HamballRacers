package dev.lyze.hamballracers.utils;

import com.badlogic.gdx.Gdx;
import de.eskalon.commons.screen.ManagedScreen;
import dev.lyze.hamballracers.HamballRacers;
import lombok.Getter;

public class ManagedScreenAdapter extends ManagedScreen {
    private final Logger<?> logger;

    @Getter
    protected final HamballRacers game;

    public ManagedScreenAdapter() {
        logger = new Logger<>(getClass());

        this.game = (HamballRacers) Gdx.app.getApplicationListener();
    }

    @Override
    protected void create() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
