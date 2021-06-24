package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.EventListener;
import dev.lyze.hamballracers.eventSystem.events.CountdownTimerFinishedEvent;
import dev.lyze.hamballracers.screens.Level;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Block {
    private final Level level;

    private boolean collision;
    private boolean icy;
    private float speedMultiplier;
    private boolean disappearAfterStart;

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
    }
}
