package dev.lyze.hamballracers.screens.map.spawners;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import dev.lyze.hamballracers.screens.Level;
import dev.lyze.hamballracers.screens.map.Map;
import dev.lyze.hamballracers.screens.map.spawners.properties.PlayerSpawnerMapProperties;
import lombok.var;

import java.util.HashMap;

public class PlayerSpawner extends MapSpawner<PlayerSpawnerMapProperties> {
    public PlayerSpawner(Level level, Map map) {
        super(level, map, PlayerSpawnerMapProperties.class);
    }

    @Override
    public void spawnInternal(MapObject object, PlayerSpawnerMapProperties properties, HashMap<MapObject, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning hamsterBall " + properties.getIndex());

        var rectangle = ((RectangleMapObject) object).getRectangle();
        level.spawnPlayer(rectangle.getX() / 2f, rectangle.getY() / 2f, properties.getIndex());
    }
}

