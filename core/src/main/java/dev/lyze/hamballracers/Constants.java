package dev.lyze.hamballracers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dev.lyze.hamballracers.assets.MainAssets;
import dev.lyze.hamballracers.eventSystem.EventManager;
import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.screens.level.characters.CharacterAnimation;
import lombok.var;

import java.util.HashMap;

public class Constants {
    public static boolean debug = false;
    public static MainAssets assets = new MainAssets();
    public static EventManager eventManager = new EventManager();

    public static Character[] characters;

    public static final Color[] playerColors = new Color[] { Color.CYAN, Color.ORANGE, Color.GREEN, Color.YELLOW };
    public static final int maxPlayers = playerColors.length;
    public static final TextureRegionDrawable[] playerBackgrounds = new TextureRegionDrawable[Constants.maxPlayers];

    public static void initialize() {
        Constants.assets.load();
        Constants.assets.finishAndConsume();

        initializeCharacterColors();
        initializeCharacters();
    }

    private static void initializeCharacterColors() {
        var pixmap = new Pixmap(Constants.maxPlayers, 1, Pixmap.Format.RGBA8888);

        for (int i = 0; i < playerBackgrounds.length; i++) {
            pixmap.setColor(Constants.playerColors[i]);
            pixmap.drawPixel(i, 0);
        }

        var texture = new Texture(pixmap);

        for (int i = 0; i < playerBackgrounds.length; i++) {
            playerBackgrounds[i] = new TextureRegionDrawable(new TextureRegion(texture, i, 0, 1, 1));
        }
    }

    private static void initializeCharacters() {
        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> lyzeAnimations = new HashMap<>();
        lyzeAnimations.put(CharacterAnimation.IDLE, assets.getMainTextureAtlas().getLyzeIdle());
        lyzeAnimations.put(CharacterAnimation.RUN, assets.getMainTextureAtlas().getLyzeNormal());
        lyzeAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getLyzeBlink());

        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> renbyAnimations = new HashMap<>();
        renbyAnimations.put(CharacterAnimation.IDLE, assets.getMainTextureAtlas().getRenbyIdle());
        renbyAnimations.put(CharacterAnimation.RUN, assets.getMainTextureAtlas().getRenbyNormal());
        renbyAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getRenbyBlink());

        characters = new Character[]{
                Character.builder()
                        .name("Lyze")
                        .preview(assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),

                Character.builder()
                        .name("Lyze")
                        .preview(assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),

                Character.builder()
                        .name("Lyze")
                        .preview(assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build()
        };
    }
}
