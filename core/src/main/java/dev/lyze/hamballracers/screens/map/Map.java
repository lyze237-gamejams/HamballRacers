package dev.lyze.hamballracers.screens.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Map {
    private static final Logger<Map> logger = new Logger<>(Map.class);

    private final TiledMap map;
    private final OrthogonalTiledMapRendererBleeding renderer;

    private Block[][] blocks;

    public Map(String file) {
        map = new TmxMapLoader().load(file);
        var layer = (TiledMapTileLayer) map.getLayers().get(0);

        renderer = new OrthogonalTiledMapRendererBleeding(map, 0.5f);

        blocks = new Block[layer.getWidth()][layer.getHeight()];

        setupCollisions();
    }

    private void setupCollisions() {
        for (MapLayer genericLayer : map.getLayers()) {
            if (!(genericLayer instanceof TiledMapTileLayer))
                continue;

            var layer = (TiledMapTileLayer) genericLayer;

            for (int x = 0; x < layer.getWidth(); x++) {
                for (int y = 0; y < layer.getHeight(); y++) {
                    var cell = layer.getCell(x, y);

                    if (blocks[x][y] == null)
                        blocks[x][y] = new Block(x, y);

                    if (cell == null)
                        continue;

                    if (cell.getTile().getObjects().getCount() == 1) {
                        blocks[x][y].setCollision(true);
                    }

                    if (cell.getTile().getProperties().get("slippery", false, Boolean.class))
                        blocks[x][y].setSlippery(true);
                }
            }
        }
    }

    public void render(OrthographicCamera cam) {
        renderer.setView(cam);
        renderer.render();
    }

    public void debugRender(ShapeDrawer drawer) {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                blocks[x][y].debugRender(drawer);
            }
        }
    }

    public Block getBlock(float x, float y) {
        return blocks[(int) (x / 8f)][(int) (y / 8f)];
    }

    public TiledMapTileLayer getLayer() {
        return (TiledMapTileLayer) map.getLayers().get(0);
    }
}
