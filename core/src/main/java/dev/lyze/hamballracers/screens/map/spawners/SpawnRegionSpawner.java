package dev.lyze.hamballracers.screens.map.spawners;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import dev.lyze.hamballracers.screens.Level;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.screens.map.spawners.properties.SpawnRegionSpawnerProperties;

import java.util.HashMap;

public class SpawnRegionSpawner extends MapSpawner<SpawnRegionSpawnerProperties> {
    public SpawnRegionSpawner(Level level, Map map) {
        super(level, map, SpawnRegionSpawnerProperties.class);
    }

    @Override
    public void spawnInternal(MapObject object, SpawnRegionSpawnerProperties properties, HashMap<MapObject, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning spawn");
    }
}

