package dev.lyze.hamballracers.screens.level.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.EventListener;
import dev.lyze.hamballracers.eventSystem.events.CountdownTimerFinishedEvent;
import dev.lyze.hamballracers.screens.level.Level;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Block {
    private final Level level;

    private boolean collision;
    private boolean icy;
    private float speedMultiplier;
    private float speedMultiplierTime;
    private boolean disappearAfterStart;
    private boolean forceSpeedMultiplierPenalty;
    private boolean chargeNitro;

    private int x, y;

    public Block(Level level, int x, int y, TiledMapTileLayer.Cell cell) {
        this.level = level;
        this.x = x;
        this.y = y;

        update(cell);
    }

    public void debugRender(ShapeDrawer drawer) {
        if (collision) {
            drawer.setColor(Color.RED);
            drawer.rectangle(x * 8f, y * 8f, 8, 8);
        }

        if (icy) {
            drawer.setColor(Color.TEAL);
            drawer.rectangle(x * 8f + 1, y * 8f + 1, 6, 6);
        }
    }

    public void update(TiledMapTileLayer.Cell cell) {
        if (cell == null)
            return;

        if (cell.getTile().getObjects().getCount() == 1)
            setCollision(true);

        if (cell.getTile().getProperties().get("icy", false, Boolean.class))
            setIcy(true);

        if (cell.getTile().getProperties().get("disappearAfterStart", false, Boolean.class)) {
            Constants.eventManager.addListener(new EventListener<CountdownTimerFinishedEvent>(CountdownTimerFinishedEvent.class) {
                @Override
                protected void fire(CountdownTimerFinishedEvent event) {
                    cell.setTile(null);
                    collision = false;
                }
            });
        }

        setSpeedMultiplier(cell.getTile().getProperties().get("speed", 1f, Float.class));
        setSpeedMultiplierTime(cell.getTile().getProperties().get("speedMultiplierTime", 0f, Float.class));

        if (cell.getTile().getProperties().get("forceSpeedMultiplierPenalty", false, Boolean.class))
            setForceSpeedMultiplierPenalty(true);

        if (cell.getTile().getProperties().get("chargeNitro", false, Boolean.class))
            setChargeNitro(true);
    }
}