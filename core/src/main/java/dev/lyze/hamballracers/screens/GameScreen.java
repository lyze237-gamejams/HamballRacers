package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen extends ManagedScreenAdapter {
    private final SpriteBatch batch;
    private final ShapeDrawer drawer;

    private Level level;

    public GameScreen() {
        batch = new SpriteBatch();
        drawer = new ShapeDrawer(batch, Constants.assets.getMainTextureAtlas().getPixel());
        drawer.setDefaultLineWidth(0.5f);
    }

    @Override
    public void show() {
        level = new Level(this, (GameType) pushParams[0]);

        super.show();
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
