package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

    public boolean isCollision() {
        if (collision)
            return true;

        if (disappearAfterStart && !level.getHud().getCountdown().isFinished())
            return true;

        return false;
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

        if (cell.getTile().getProperties().get("disappearAfterStart", false, Boolean.class))
            setDisappearAfterStart(true);


        setSpeedMultiplier(cell.getTile().getProperties().get("speed", 1f, Float.class));
    }
}
