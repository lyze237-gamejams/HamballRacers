package dev.lyze.hamballracers.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import dev.lyze.hamballracers.assets.utils.DynamicTextureAtlas;
import dev.lyze.hamballracers.assets.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class MainTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("hamsterballs/balls")
    private Array<TextureAtlas.AtlasRegion> hamsterball;

    @Getter @LoadFromTextureAtlas("other/logo")
    private TextureAtlas.AtlasRegion logo;

    @Getter @LoadFromTextureAtlas("other/pixel")
    private TextureAtlas.AtlasRegion pixel;

    public MainTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
