package dev.lyze.hamballracers.utils.camera;

import com.badlogic.gdx.math.Vector2;
import com.gempukku.libgdx.lib.camera2d.focus.PositionProvider;
import dev.lyze.hamballracers.screens.entities.Entity;

public class EntityPositionProvider implements PositionProvider {
    private final Entity entity;

    public EntityPositionProvider(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Vector2 getPosition(Vector2 position) {
        return position.set(entity.getX(), entity.getY());
    }
}
