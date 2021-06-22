package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Block {
    private boolean collision;
    private boolean icy;
    private float speedMultiplier;

    private int x, y;

    public Block(int x, int y, TiledMapTileLayer.Cell cell) {
        this.x = x;
        this.y = y;

        if (cell == null)
            return;

        if (cell.getTile().getObjects().getCount() == 1)
            setCollision(true);

        if (cell.getTile().getProperties().get("icy", false, Boolean.class))
            setIcy(true);

        setSpeedMultiplier(cell.getTile().getProperties().get("speed", 0f, Float.class));
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
}
