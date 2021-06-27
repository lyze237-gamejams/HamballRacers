package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.screens.level.map.Track;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.input.PlayerInputListener;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class GameScreen extends ManagedScreenAdapter implements PlayerInputListener {
    private final SpriteBatch batch;
    private final ShapeDrawer drawer;

    private Player[] players;
    private Level level;

    private final ArrayList<Player> playersDisconnected = new ArrayList<>();

    public GameScreen() {
        batch = new SpriteBatch();
        drawer = new ShapeDrawer(batch, Constants.assets.getMainTextureAtlas().getPixel());
        drawer.setDefaultLineWidth(0.5f);
    }

    @Override
    public void show() {
        players = (Player[]) pushParams[0];
        var track = (Track) pushParams[1];

        Constants.gamepadMapping.addListener(this);

        level = new Level(this, players, track, 3);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        super.show();
    }

    @Override
    public void hide() {
        Constants.gamepadMapping.removeListener(this);

        Constants.eventManager.removeListenersOfBind(level);

        level.dispose();
    }

    private float actualDeltaTime = 0.0f;
    private final float targetDeltaTime = 0.01f;
    private double currentTime = System.currentTimeMillis();
    private float accumulator = 0f;

    @Override
    public void render(float delta) {
        if (!playersDisconnected.isEmpty())
            return;

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

    @Override
    public void onDeregistered(VirtualGamepad gamepad, int index, boolean disconnected) {
        for (Player player : players) {
            if (player != null && player.getPlayerIndex() == index) {
                if (disconnected)
                    playersDisconnected.add(player);

                return;
            }
        }
    }

    @Override
    public void onRegistered(VirtualGamepad gamepad, int index) {
        for (Player player : players) {
            if (player != null && player.getPlayerIndex() == index) {
                playersDisconnected.remove(player);
            }
        }
    }

    @Override
    public void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {

    }

    @Override
    public void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {

    }
}
