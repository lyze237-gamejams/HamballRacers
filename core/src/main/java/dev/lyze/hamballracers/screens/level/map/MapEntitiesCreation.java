package dev.lyze.hamballracers.screens.level.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.map.spawners.MapSpawner;
import dev.lyze.hamballracers.screens.level.map.spawners.PlayerSpawner;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;

import java.util.ArrayList;
import java.util.HashMap;

public class MapEntitiesCreation {
    private static final Logger<MapEntitiesCreation> logger = new Logger<>(MapEntitiesCreation.class);

    private final Level level;
    private final Map map;

    private final ArrayList<MapSpawner<?>> mapSpawners = new ArrayList<>();

    private final HashMap<MapObject, MapSpawner<?>> entities = new HashMap<>();
    private final HashMap<MapObject, MapProperties> spawnedEntities = new HashMap<>();

    public MapEntitiesCreation(Level level, Map map) {
        this.level = level;
        this.map = map;

        mapSpawners.add(new PlayerSpawner(level, map));
    }

    public void initialize() {
        for (MapLayer layer : map.getMap().getLayers()) {
            initializeLayer(layer);
        }
    }

    private void initializeLayer(MapLayer layer) {
        logger.logInfo("Parsing layer " + layer.getName() + " for map objects");
        for (MapObject object : layer.getObjects()) {
            var rectangle = ((RectangleMapObject) object).getRectangle();

            var properties = object.getProperties();
            var type = properties.get("type", String.class);
            if (type == null)
                continue;

            var spawner = mapSpawners.stream().filter(t -> t.getClass().getSimpleName().equals(type)).findFirst().orElse(null);
            if (spawner == null) {
                logger.logError("Couldn't find appropriate spawner " + type + " for properties of cell " + rectangle.getX() + "/" + rectangle.getY());
                throw new NullPointerException();
            }

            entities.put(object, spawner);
        }

        for (int cnt = 0; spawnedEntities.size() < entities.size(); cnt++) {
            for (MapObject obj : entities.keySet()) {

                if (obj.getProperties().get("step", 0, Integer.class) == cnt) {
                    var spawnedProperties = spawn(obj, entities.get(obj));
                    spawnedEntities.put(obj, spawnedProperties);
                }
            }
        }
    }

    public MapProperties spawn(MapObject object, MapSpawner<?> spawner) {
        try {
            var instance = (MapProperties) ClassReflection.newInstance(spawner.getPropertiesClass());
            for (Field field : ClassReflection.getDeclaredFields(spawner.getPropertiesClass())) {
                initializeMapProperties(object, instance, field);
            }

            spawner.spawn(object, instance, spawnedEntities);

            return instance;
        } catch (ReflectionException e) {
            logger.logError("Couldn't create " + spawner.getPropertiesClass().getSimpleName(), e);
            throw new IllegalArgumentException();
        }
    }

    private void initializeMapProperties(MapObject object, MapProperties instance, Field field) throws ReflectionException {
        field.setAccessible(true);

        var value = object.getProperties().get(field.getName());
        if (field.getType().isEnum()) {
            if (value.toString().equals("null"))
                value = null;
            else
                value = Enum.valueOf((Class<Enum>) field.getType(), value.toString());
        }

        field.set(instance, value);
    }
}

