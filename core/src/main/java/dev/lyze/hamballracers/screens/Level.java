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
import dev.lyze.hamballracers.screens.entities.HamsterBall;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.utils.camera.EntityPositionProvider;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;

public class Level {
    private final GameScreen screen;

    private final Viewport viewport;
    private final HamsterBall[] hamsterBalls;

    @Getter
    private final Map map;

    private final FocusCameraController camera;

    public Level(GameScreen screen, GameType type) {
        this.screen = screen;

        map = new Map(Constants.Assets.getMap());

        hamsterBalls = new HamsterBall[type.getPlayerCount()];
        for (int i = 0; i < hamsterBalls.length; i++)
            hamsterBalls[i] = new HamsterBall(this, 32 + 32 * i, 32 + 32 * i, i);

        viewport = new ExtendViewport(240, 135);

        var cameraFoci = Arrays.stream(hamsterBalls).map(hamsterBall -> new EntityFocus(new EntityPositionProvider(hamsterBall))).toArray(EntityFocus[]::new);
        camera = new FocusCameraController(viewport.getCamera(),
                new FitAllCameraFocus(cameraFoci),
                new LockedToCameraConstraint(new Vector2(0.5f, 0.5f)),
                new FitAllCameraConstraint(new Rectangle(0.2f, 0.2f, 0.6f, 0.6f), cameraFoci),
                new MinimumViewportCameraConstraint(240, 135));
    }

    public void update(float delta) {
        Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.update(delta));

        viewport.apply();
        camera.update(delta);
    }

    public void render(SpriteBatch batch, ShapeDrawer drawer) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        map.render(((OrthographicCamera) viewport.getCamera()));
        Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.render(batch));

        if (Constants.Debug) {
            map.debugRender(drawer);
            Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.debugRender(drawer));
        }

        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public boolean isHamsterBallCollision(int currentPlayerIndex, float x, float y) {
        var currentPlayerRectangle = hamsterBalls[currentPlayerIndex].getHitbox().generateRectangle(x, y);

        for (int i = 0; i < hamsterBalls.length; i++) {
            if (i == currentPlayerIndex)
                continue;

            HamsterBall otherPlayer = hamsterBalls[i];
            var otherPlayerRectangle = otherPlayer.getHitbox().generateRectangle(otherPlayer.getX(), otherPlayer.getY());

            if (currentPlayerRectangle.overlaps(otherPlayerRectangle))
                return true;
        }

        return false;
    }
}
