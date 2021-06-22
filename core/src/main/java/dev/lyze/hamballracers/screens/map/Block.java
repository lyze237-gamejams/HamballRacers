package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Block {
    private boolean collision;
    private boolean icy;
    private float speedMultiplier;

    private static BitmapFont font;

    private int x, y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;

        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(0.2f);
        }
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

        if (speedMultiplier != 1.0f)
            font.draw(drawer.getBatch(), String.valueOf(speedMultiplier), x * 8 + 2f, y * 8 + 4f);
    }
}
