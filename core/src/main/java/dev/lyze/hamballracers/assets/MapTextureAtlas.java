package dev.lyze.hamballracers.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.hamballracers.assets.utils.DynamicTextureAtlas;
import dev.lyze.hamballracers.assets.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class MapTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("desert")
    private TextureAtlas.AtlasRegion desert;

    public MapTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
