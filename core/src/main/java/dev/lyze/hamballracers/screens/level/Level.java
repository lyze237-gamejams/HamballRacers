package dev.lyze.hamballracers.screens.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import com.gempukku.libgdx.lib.camera2d.constraint.FitAllCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.LockedToCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.MinimumViewportCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.focus.EntityFocus;
import com.gempukku.libgdx.lib.camera2d.focus.FitAllCameraFocus;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.GameScreen;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import dev.lyze.hamballracers.screens.level.map.Map;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.camera.EntityPositionProvider;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;

public class Level {
    private static final Logger<Level> logger = new Logger<>(Level.class);
    @Getter
    private final GameScreen screen;

    private final Viewport viewport;

    @Getter
    private final HamsterBall[] hamsterBalls;

    @Getter
    private final Map map;

    private final FocusCameraController camera;

    @Getter
    private final LevelHud hud;

    public Level(GameScreen screen, Player[] players) {
        this.screen = screen;

        viewport = new ExtendViewport(240, 135);

        var hamBalls = new Array<HamsterBall>();
        for (Player player : players)
            if (player != null)
                hamBalls.add(new HamsterBall(this, player));

        hamsterBalls = new HamsterBall[hamBalls.size];
        for (int i = 0; i < hamsterBalls.length; i++)
            hamsterBalls[i] = hamBalls.get(i);

        var cameraFoci = Arrays.stream(hamsterBalls).map(hamsterBall -> new EntityFocus(new EntityPositionProvider(hamsterBall))).toArray(EntityFocus[]::new);
        camera = new FocusCameraController(viewport.getCamera(),
                new FitAllCameraFocus(cameraFoci),
                new LockedToCameraConstraint(new Vector2(0.5f, 0.5f)),
                new FitAllCameraConstraint(new Rectangle(0.2f, 0.2f, 0.6f, 0.6f), cameraFoci),
                new MinimumViewportCameraConstraint(240, 135));

        hud = new LevelHud(this);

        map = new Map(this, Constants.assets.getMap());
    }

    public void update(float delta) {
        if (!screen.getGame().getScreenManager().inTransition())
            Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.update(delta));

        hud.act();

        viewport.apply();
        camera.update(delta);
    }

    public void render(SpriteBatch batch, ShapeDrawer drawer) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        map.render(((OrthographicCamera) viewport.getCamera()));
        Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.render(batch, drawer));

        if (Constants.debug) {
            map.debugRender(drawer);
            Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.debugRender(drawer));
        }

        batch.end();

        hud.getViewport().apply();
        hud.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        hud.getViewport().update(width, height, true);
    }

    private final  Rectangle currentPlayerRectangle = new Rectangle();
    private final  Rectangle otherPlayerRectangle = new Rectangle();
    public boolean isHamsterBallCollision(HamsterBall self, float x, float y) {
        self.getHitbox().generateRectangle(x, y, currentPlayerRectangle);

        for (HamsterBall other : hamsterBalls) {
            if (other == self)
                continue;

            other.getHitbox().generateRectangle(other.getX(), other.getY(), otherPlayerRectangle);

            if (currentPlayerRectangle.overlaps(otherPlayerRectangle))
                return true;
        }

        return false;
    }

    public void spawnPlayer(float x, float y, int index) {
        for (HamsterBall hamsterBall : hamsterBalls) {
            if (hamsterBall.getPlayer().getPlayerIndex() == index) {
                hamsterBall.setX(x);
                hamsterBall.setY(y);

                return;
            }
        }
    }
}
