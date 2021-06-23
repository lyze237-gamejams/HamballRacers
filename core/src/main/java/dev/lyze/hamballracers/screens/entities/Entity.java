package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.hamballracers.screens.map.Map;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {
    protected final Map map;
    @Getter
    protected float x, y;

    public Entity(Map map, float x, float y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void debugRender(ShapeDrawer drawer);
}
