package dev.lyze.hamballracers.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import dev.lyze.hamballracers.assets.utils.DynamicTextureAtlas;
import dev.lyze.hamballracers.assets.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class MainTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("players/ball")
    private Array<TextureAtlas.AtlasRegion> hamsterBall;

    @Getter @LoadFromTextureAtlas("players/lyze/normal")
    private Array<TextureAtlas.AtlasRegion> lyzeNormal;

    @Getter @LoadFromTextureAtlas("players/lyze/blink")
    private Array<TextureAtlas.AtlasRegion> lyzeBlink;

    @Getter @LoadFromTextureAtlas("players/lyze/idle")
    private Array<TextureAtlas.AtlasRegion> lyzeIdle;

    @Getter @LoadFromTextureAtlas("players/renby/normal")
    private Array<TextureAtlas.AtlasRegion> renbyNormal;

    @Getter @LoadFromTextureAtlas("players/renby/blink")
    private Array<TextureAtlas.AtlasRegion> renbyBlink;

    @Getter @LoadFromTextureAtlas("players/renby/idle")
    private Array<TextureAtlas.AtlasRegion> renbyIdle;

    @Getter @LoadFromTextureAtlas("players/jake/normal")
    private Array<TextureAtlas.AtlasRegion> jakeNormal;

    @Getter @LoadFromTextureAtlas("players/jake/blink")
    private Array<TextureAtlas.AtlasRegion> jakeBlink;

    @Getter @LoadFromTextureAtlas("players/jake/idle")
    private Array<TextureAtlas.AtlasRegion> jakeIdle;

    @Getter @LoadFromTextureAtlas("players/zooty/normal")
    private Array<TextureAtlas.AtlasRegion> zootyNormal;

    @Getter @LoadFromTextureAtlas("players/zooty/blink")
    private Array<TextureAtlas.AtlasRegion> zootyBlink;

    @Getter @LoadFromTextureAtlas("players/zooty/idle")
    private Array<TextureAtlas.AtlasRegion> zootyIdle;

    @Getter @LoadFromTextureAtlas("players/bree/normal")
    private Array<TextureAtlas.AtlasRegion> breeNormal;

    @Getter @LoadFromTextureAtlas("players/bree/blink")
    private Array<TextureAtlas.AtlasRegion> breeBlink;

    @Getter @LoadFromTextureAtlas("players/bree/idle")
    private Array<TextureAtlas.AtlasRegion> breeIdle;


    @Getter @LoadFromTextureAtlas("other/logo")
    private TextureAtlas.AtlasRegion logo;

    @Getter @LoadFromTextureAtlas("other/pixel")
    private TextureAtlas.AtlasRegion pixel;

    @Getter @LoadFromTextureAtlas("other/questionmark")
    private TextureAtlas.AtlasRegion questionmark;

    @Getter @LoadFromTextureAtlas("other/transparentBackground")
    private TextureAtlas.AtlasRegion transparentBackground;

    @Getter @LoadFromTextureAtlas("skies/24_PixelSky")
    private Array<TextureAtlas.AtlasRegion> sky;


    public MainTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
