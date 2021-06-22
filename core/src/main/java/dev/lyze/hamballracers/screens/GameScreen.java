package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen extends ManagedScreenAdapter {
    private final SpriteBatch batch;
    private final ShapeDrawer drawer;

    private final Level level;

    public GameScreen() {
        batch = new SpriteBatch();
        drawer = new ShapeDrawer(batch, new TextureRegion(new Texture(Gdx.files.internal("pixel.png"))));
        drawer.setDefaultLineWidth(0.5f);

        level = new Level(this, "map/map.tmx");
    }

    private float actualDeltaTime = 0.0f;
    private final float targetDeltaTime = 0.01f;
    private double currentTime = System.currentTimeMillis();
    private float accumulator = 0f;

    @Override
    public void render(float delta) {
        var newTime = System.currentTimeMillis();
        var frameTime = (newTime - currentTime) / 1000f;
        accumulator += frameTime;
        currentTime = newTime;

        while (accumulator >= targetDeltaTime) {
            level.update(actualDeltaTime);

            accumulator -= targetDeltaTime;
            actualDeltaTime = targetDeltaTime;
        }

        level.render(batch, drawer);
    }

    @Override
    public void resize(int width, int height) {
        level.resize(width, height);
    }
}
