package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntArray;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.input.PlayerInputListener;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameScreen extends ManagedScreenAdapter implements PlayerInputListener {
    private final SpriteBatch batch;
    private final ShapeDrawer drawer;

    private Player[] players;
    private Level level;

    private final ArrayList<Player> playersDiconnected = new ArrayList<>();

    public GameScreen() {
        batch = new SpriteBatch();
        drawer = new ShapeDrawer(batch, Constants.assets.getMainTextureAtlas().getPixel());
        drawer.setDefaultLineWidth(0.5f);
    }

    @Override
    public void show() {
        players = (Player[]) pushParams;

        Constants.gamepadMapping.addListener(this);
        var playerOrder = Arrays.stream(players).filter(Objects::nonNull).mapToInt(Player::getPlayerIndex).toArray();
        var fullOrder = new IntArray(Constants.maxPlayers);

        for (int o : playerOrder)
            fullOrder.add(o);

        for (int i = 0; i < Constants.maxPlayers; i++)
            if (!fullOrder.contains(i))
                fullOrder.add(i);

        var shrink = fullOrder.shrink();

        Constants.gamepadMapping.setReconnectOrder(shrink);
        level = new Level(this, players);

        super.show();
    }

    @Override
    public void hide() {
        Constants.gamepadMapping.removeListener(this);
    }

    private float actualDeltaTime = 0.0f;
    private final float targetDeltaTime = 0.01f;
    private double currentTime = System.currentTimeMillis();
    private float accumulator = 0f;

    @Override
    public void render(float delta) {
        if (!playersDiconnected.isEmpty())
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
                player.setGamepad(null);

                if (disconnected)
                    playersDiconnected.add(player);

                return;
            }
        }
    }

    @Override
    public void onRegistered(VirtualGamepad gamepad, int index) {
        for (Player player : players) {
            if (player != null && player.getPlayerIndex() == index) {
                player.setGamepad(gamepad);

                playersDiconnected.remove(player);
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
