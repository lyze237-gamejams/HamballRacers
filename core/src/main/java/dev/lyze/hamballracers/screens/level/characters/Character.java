package dev.lyze.hamballracers.screens.level.characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class Character {
    private String name;
    private TextureRegion preview;

    private HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> animations;
}
