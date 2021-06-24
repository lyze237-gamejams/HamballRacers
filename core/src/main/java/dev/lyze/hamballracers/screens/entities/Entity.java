package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {
    @Getter @Setter
    protected float x, y;

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch, ShapeDrawer drawer);

    public abstract void debugRender(ShapeDrawer drawer);
}
