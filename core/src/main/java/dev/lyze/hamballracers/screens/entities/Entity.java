package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.hamballracers.screens.map.Map;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {
    protected final Map map;
    protected float x, y;

    public Entity(Map map, float x, float y) {
        this.map = map;
        this.x = x;
        this.y = y;
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
}
