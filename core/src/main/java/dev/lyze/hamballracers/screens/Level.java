package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import com.gempukku.libgdx.lib.camera2d.constraint.FitAllCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.LockedToCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.MinimumViewportCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.focus.EntityFocus;
import com.gempukku.libgdx.lib.camera2d.focus.FitAllCameraFocus;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.entities.Player;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.utils.camera.EntityPositionProvider;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;

public class Level {
    private final GameScreen screen;

    private final Viewport viewport;
    private final Player[] players;

    private final Map map;

    private final FocusCameraController camera;

    public Level(GameScreen screen, String mapPath) {
        this.screen = screen;

        map = new Map(mapPath);
        players = new Player[] {
            new Player(map, 16, 16, 0),
            new Player(map, 16, 16, 1)
        };

        viewport = new ExtendViewport(240, 135);

        var cameraFoci = Arrays.stream(players).map(player -> new EntityFocus(new EntityPositionProvider(player))).toArray(EntityFocus[]::new);
        camera = new FocusCameraController(viewport.getCamera(),
                new FitAllCameraFocus(cameraFoci),
                new LockedToCameraConstraint(new Vector2(0.5f, 0.5f)),
                new FitAllCameraConstraint(new Rectangle(0.1f, 0.1f, 0.8f, 0.8f), cameraFoci),
                new MinimumViewportCameraConstraint(240, 135));
    }

    public void update(float delta) {
        Arrays.stream(players).forEach(player -> player.update(delta));

        viewport.apply();
        camera.update(delta);
    }

    public void render(SpriteBatch batch, ShapeDrawer drawer) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        map.render(((OrthographicCamera) viewport.getCamera()));
        Arrays.stream(players).forEach(player -> player.render(batch));

        if (Constants.DEBUG) {
            map.debugRender(drawer);
            Arrays.stream(players).forEach(player -> player.debugRender(drawer));
        }

        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
