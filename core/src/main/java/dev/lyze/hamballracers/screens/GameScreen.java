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

    @Override
    public void render(float delta) {
        player.update(delta);

        viewport.getCamera().position.set(player.getX() + player.getWidth() / 2f, player.getY() + player.getHeight() / 2f, 0);
        viewport.apply();

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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
