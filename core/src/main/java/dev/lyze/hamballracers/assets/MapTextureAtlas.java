package dev.lyze.hamballracers.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.hamballracers.assets.utils.DynamicTextureAtlas;
import dev.lyze.hamballracers.assets.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class MapTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("desert")
    private TextureAtlas.AtlasRegion desert;

    @Getter @LoadFromTextureAtlas("basicmap")
    private TextureAtlas.AtlasRegion basicmap;

    @Getter @LoadFromTextureAtlas("ice")
    private TextureAtlas.AtlasRegion ice;

    public MapTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
