package dev.lyze.hamballracers.screens.level.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.entities.Hitbox;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.camera.OrthogonalTiledMapRendererBleeding;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Map {
    private static final Logger<Map> logger = new Logger<>(Map.class);

    @Getter
    private final Level level;

    @Getter
    private final TiledMap map;
    private final OrthogonalTiledMapRendererBleeding renderer;

    private final Block[][] blocks;

    public Map(Level level, String file) {
        this(level, new TmxMapLoader().load(file));
    }

    public Map(Level level, TiledMap map) {
        this.level = level;
        this.map = map;
        var layer = (TiledMapTileLayer) map.getLayers().get(0);

        renderer = new OrthogonalTiledMapRendererBleeding(map, 0.5f);

        blocks = new Block[layer.getWidth()][layer.getHeight()];
        logger.logInfo("Map size is " + layer.getWidth() + " / " + layer.getHeight());

        new MapEntitiesCreation(level, this).initialize();
        setupLayers();
        setupCollisions();
    }

    private void setupLayers() {
        for (MapLayer layer : map.getLayers()) {
            if (layer.getProperties().get("Hidden", false, Boolean.class))
                layer.setVisible(false);
        }
    }

    private void setupCollisions() {
        for (MapLayer genericLayer : map.getLayers()) {
            if (!(genericLayer instanceof TiledMapTileLayer))
                continue;

            var layer = (TiledMapTileLayer) genericLayer;

            logger.logInfo("Parsing tilemap layer " + layer.getName());

            for (int x = 0; x < layer.getWidth(); x++)
                for (int y = 0; y < layer.getHeight(); y++)
                    if (blocks[x][y] == null)
                        blocks[x][y] = new Block(level, x, y, layer.getCell(x, y));
                    else
                        blocks[x][y].update(layer.getCell(x, y));
        }
    }

    public void render(OrthographicCamera cam) {
        renderer.setView(cam);
        renderer.render();
    }

    public void debugRender(ShapeDrawer drawer) {
        for (Block[] block : blocks)
            for (Block value : block)
                value.debugRender(drawer);
    }

    public Block getBlock(float x, float y) {
        return blocks[(int) (x / 8f)][(int) (y / 8f)];
    }

    public boolean isBlockCollision(float x, float y, Hitbox hitbox) {
        var topLeftX = (int) ((x - hitbox.getHalfHitboxWidth() / 2f + hitbox.getHitboxOffsetX()) / 8f);
        var topLeftY = (int) ((y - hitbox.getHalfHitboxHeight() / 2f + hitbox.getHitboxOffsetX()) / 8f);

        var bottomRightX = (int) ((x + hitbox.getHalfHitboxWidth() / 2f + hitbox.getHitboxOffsetX()) / 8f);
        var bottomRightY = (int) ((y + hitbox.getHalfHitboxHeight() / 2f + hitbox.getHitboxOffsetY()) / 8f);

        for (int xCheck = topLeftX; xCheck <= bottomRightX; xCheck++)
            for (int yCheck = topLeftY; yCheck <= bottomRightY; yCheck++)
                if (blocks[xCheck][yCheck].isCollisionTile() && blocks[xCheck][yCheck].isCollision(x, y, hitbox))
                    return true;

        return false;
    }

    public void dispose() {
        map.dispose();
    }
}
