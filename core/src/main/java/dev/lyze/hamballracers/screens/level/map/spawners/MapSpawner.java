package dev.lyze.hamballracers.screens.level.map.spawners;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.map.Map;
import dev.lyze.hamballracers.utils.Logger;
import lombok.Getter;

import java.util.HashMap;

public abstract class MapSpawner<TProperties extends MapProperties> {
    protected final Logger<?> logger;

    protected final Level level;
    protected final Map map;

    @Getter
    private final Class<TProperties> propertiesClass;

    public MapSpawner(Level level, Map map, Class<TProperties> propertiesClass) {
        this.level = level;
        this.map = map;
        this.propertiesClass = propertiesClass;

        logger = new Logger<>(getClass());
    }

    public void spawn(MapObject object, MapProperties data, HashMap<MapObject, MapProperties> spawnedEntities) {
        spawnInternal(object, (TProperties) data, spawnedEntities);
    }

    public abstract void spawnInternal(MapObject object, TProperties data, HashMap<MapObject, MapProperties> spawnedEntities);
}
