package dev.lyze.hamballracers.screens.level;

import com.badlogic.gdx.graphics.Color;
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
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.EventListener;
import dev.lyze.hamballracers.eventSystem.events.CountdownTimerFinishedEvent;
import dev.lyze.hamballracers.screens.GameScreen;
import dev.lyze.hamballracers.screens.VictoryScreen;
import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import dev.lyze.hamballracers.screens.level.hud.LevelHud;
import dev.lyze.hamballracers.screens.level.map.Map;
import dev.lyze.hamballracers.screens.level.map.Track;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.camera.EntityPositionProvider;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;
import java.util.HashMap;

public class Level {
    private static final Logger<Level> logger = new Logger<>(Level.class);
    @Getter
    private final GameScreen screen;

    private final Viewport viewport;

    @Getter
    private final HamsterBall[] hamsterBalls;

    @Getter
    private final HashMap<Integer, Rectangle> checkpoints = new HashMap<>();

    @Getter
    private final Map map;

    private final FocusCameraController camera;

    @Getter
    private final LevelHud hud;
    @Getter
    private final int lapCount;
    private final Track track;

    @Getter
    private long levelStartTime = 0;

    public Level(GameScreen screen, Player[] players, Track track, int lapCount) {
        this.screen = screen;
        this.lapCount = lapCount;
        this.track = track;

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

        hud = new LevelHud(this, hamsterBalls);

        map = new Map(this, track.getMap());

        Constants.eventManager.addListener(new EventListener<CountdownTimerFinishedEvent>(CountdownTimerFinishedEvent.class, this) {
            @Override
            protected void fire(CountdownTimerFinishedEvent event) {
                levelStartTime = System.currentTimeMillis();
            }
        });
    }

    public void update(float delta) {
        if (!screen.getGame().getScreenManager().inTransition())
            Arrays.stream(hamsterBalls).forEach(hamsterBall -> hamsterBall.update(delta));

        var finished = Arrays.stream(hamsterBalls).allMatch(h -> h.getCurrentLap() >= h.getLaps().length && h.getVelocity().x < 1 && h.getVelocity().y < 1);
        if (finished && !screen.getGame().getScreenManager().inTransition())
            screen.getGame().getScreenManager().pushScreen(VictoryScreen.class.getName(), BlendingTransition.class.getName(), hamsterBalls, track, levelStartTime);

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
            drawer.setColor(Color.BLUE);
            checkpoints.values().forEach(drawer::rectangle);
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
        logger.logInfo("Spawn player called with parameters " + x + "/" + y + ": " + index);
        for (HamsterBall hamsterBall : hamsterBalls) {
            if (hamsterBall.getPlayer().getPlayerIndex() == index) {
                logger.logInfo("Setting hamball player index " + index + " to " + x + "/" + y);
                hamsterBall.setX(x);
                hamsterBall.setY(y);

                return;
            }
        }
    }

    public void addCheckpoint(float x, float y, float width, float height, int index) {
        var rect = new Rectangle(x, y, width, height);
        logger.logInfo("Adding checkpoint: " + rect);
        checkpoints.put(index, rect);

        hud.getCheckpointTimer().updateCheckpoints();
    }

    public float getLevelElapsedTime() {
        return (System.currentTimeMillis() - levelStartTime) / 1000f;
    }

    public void dispose() {
        map.dispose();

        for (HamsterBall hamsterBall : hamsterBalls)
            hamsterBall.dispose();
    }
}
