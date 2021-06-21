package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.graphics.Color;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Block {
    private boolean collision;
    private boolean slippery;

    private int x, y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void debugRender(ShapeDrawer drawer) {
        if (slippery) {
            drawer.setColor(0, 0.5f, 0.5f, 0.3f);
            drawer.filledRectangle(x * 8f, y * 8f, 8, 8);
        }

        if (collision) {
            drawer.setColor(Color.RED);
            drawer.rectangle(x * 8f, y * 8f, 8, 8);
        }
    }
}
