package dev.lyze.hamballracers.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.lyze.hamballracers.assets.utils.DynamicAssets;
import dev.lyze.hamballracers.assets.utils.DynamicTextureAtlasAssetLoader;
import dev.lyze.hamballracers.assets.utils.InternalOrExternalFileHandleResolver;
import dev.lyze.hamballracers.assets.utils.LoadAssetFromFile;
import lombok.Getter;
import lombok.var;

public class MainAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("music/Kouzerumatsu-SAR-Theme.mp3")
    private Music themeSong;

    @Getter @LoadAssetFromFile("atlases/main.atlas")
    private MainTextureAtlas mainTextureAtlas;

    @Getter @LoadAssetFromFile("atlases/map.atlas")
    private MapTextureAtlas mapTextureAtlas;

    @Getter @LoadAssetFromFile("map/desert.tmx")
    private TiledMap desertMap;

    @Getter @LoadAssetFromFile("skins/skin.json")
    private Skin skin;

    @Override
    protected AssetManager generateAssMan() {
        var assMan = new AssetManager();
        assMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalOrExternalFileHandleResolver()));
        for (Class<?> textureAtlasClass : new Class<?>[] { MainTextureAtlas.class, MapTextureAtlas.class }) {
            assMan.setLoader(textureAtlasClass, new DynamicTextureAtlasAssetLoader(new InternalOrExternalFileHandleResolver(), textureAtlasClass));
        }

        return assMan;
    }
}
