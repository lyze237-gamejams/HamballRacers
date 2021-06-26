package dev.lyze.hamballracers.screens.level.map.spawners;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.map.Map;
import dev.lyze.hamballracers.screens.level.map.spawners.properties.CheckpointSpawnerMapProperties;
import lombok.var;

import java.util.HashMap;

public class CheckpointSpawner extends MapSpawner<CheckpointSpawnerMapProperties> {
    public CheckpointSpawner(Level level, Map map) {
        super(level, map, CheckpointSpawnerMapProperties.class);
    }

    @Override
    public void spawnInternal(MapObject object, CheckpointSpawnerMapProperties properties, HashMap<MapObject, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning checkpoint " + properties.getIndex());

        var rectangle = ((RectangleMapObject) object).getRectangle();
        level.addCheckpoint(rectangle.getX() / 2f, rectangle.getY() / 2f, rectangle.getWidth() / 2f, rectangle.getHeight() / 2f, properties.getIndex());
    }
}

