package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.hamballracers.screens.map.Map;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {
    protected final Map map;
    protected float x, y;
    protected float width, height;

    public Entity(Map map, float x, float y, float width, float height) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void debugRender(ShapeDrawer drawer);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
