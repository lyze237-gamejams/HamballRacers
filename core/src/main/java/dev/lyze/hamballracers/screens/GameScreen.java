package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import com.gempukku.libgdx.lib.camera2d.constraint.SnapToWindowCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.focus.EntityFocus;
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

    private final FocusCameraController camera;

    public GameScreen() {
        batch = new SpriteBatch();
        drawer = new ShapeDrawer(batch, new TextureRegion(new Texture(Gdx.files.internal("pixel.png"))));
        drawer.setDefaultLineWidth(0.5f);

        map = new Map("map/map.tmx");
        player = new Player(map, 16, 16, 16, 16);

        viewport = new ExtendViewport(240, 135);
        camera = new FocusCameraController(viewport.getCamera(),
                new EntityFocus(position -> new Vector2(player.getX(), player.getY())),
                new SnapToWindowCameraConstraint(new Rectangle(0.45f, 0.45f, 0.125f, 0.125f), new Vector2(1f, 1f)));
    }

    private void update(float delta) {
        player.update(delta);

        camera.update(delta);
        viewport.apply();
    }

    private void render() {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        map.render(((OrthographicCamera) viewport.getCamera()));
        player.render(batch);

        if (Constants.DEBUG) {
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
