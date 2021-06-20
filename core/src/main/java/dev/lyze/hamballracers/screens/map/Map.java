package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.lyze.hamballracers.utils.Logger;

public class Map {
    private static final Logger<Map> logger = new Logger<>(Map.class);

    private final TiledMap map;
    private final OrthogonalTiledMapRendererBleeding renderer;

    public Map(String file) {
        map = new TmxMapLoader().load(file);
        renderer = new OrthogonalTiledMapRendererBleeding(map, 1 / 16f);

    }
}
