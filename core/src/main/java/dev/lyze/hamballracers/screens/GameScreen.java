package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.entities.Player;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen extends ManagedScreenAdapter {
    private final SpriteBatch batch;
    private final ShapeDrawer drawer;

    private final Viewport viewport;
    private final Player player;

    private final Map map;

    public GameScreen() {
        batch = new SpriteBatch();
        drawer = new ShapeDrawer(batch, new TextureRegion(new Texture(Gdx.files.internal("pixel.png"))));
        drawer.setDefaultLineWidth(0.5f);

        viewport = new ExtendViewport(240, 135);

        map = new Map("map/map.tmx");
        player = new Player(map, 16, 16, 16, 16);
    }

    private void update(float delta) {
        player.update(delta);

        viewport.getCamera().position.set(player.getX() + player.getWidth() / 2f, player.getY() + player.getHeight() / 2f, 0);
        viewport.apply();
    }

    private void render() {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        map.render(((OrthographicCamera) viewport.getCamera()));
        player.render(batch);

        if (Constants.DEBUG) {
            float gridSize = 8f;

            drawer.setColor(Color.DARK_GRAY);

            for (int x = -100; x < 100; x++)
                drawer.line(x * gridSize, -100 * gridSize, x * gridSize, 100 * gridSize);

            for (int y = -100; y < 100; y++)
                drawer.line(-100 * gridSize, y * gridSize, 100 * gridSize, y * gridSize);

            map.debugRender(drawer);
            player.debugRender(drawer);
        }

        batch.end();
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
            update(actualDeltaTime);

            accumulator -= targetDeltaTime;
            actualDeltaTime = targetDeltaTime;
        }

        render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
