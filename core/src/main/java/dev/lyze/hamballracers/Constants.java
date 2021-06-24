package dev.lyze.hamballracers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import dev.lyze.hamballracers.assets.MainAssets;
import dev.lyze.hamballracers.eventSystem.EventManager;
import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.screens.level.characters.CharacterAnimation;

import java.util.HashMap;

public class Constants {
    public static boolean Debug = false;
    public static MainAssets Assets = new MainAssets();
    public static EventManager eventManager = new EventManager();

    public static Character[] characters;

    public static void initialize() {
        Constants.Assets.load();
        Constants.Assets.finishAndConsume();

        initializeCharacters();
    }

    private static void initializeCharacters() {
        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> lyzeAnimations = new HashMap<>();
        lyzeAnimations.put(CharacterAnimation.IDLE, Assets.getMainTextureAtlas().getLyzeIdle());
        lyzeAnimations.put(CharacterAnimation.RUN, Assets.getMainTextureAtlas().getLyzeNormal());
        lyzeAnimations.put(CharacterAnimation.BLINK, Assets.getMainTextureAtlas().getLyzeBlink());

        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> renbyAnimations = new HashMap<>();
        renbyAnimations.put(CharacterAnimation.IDLE, Assets.getMainTextureAtlas().getRenbyIdle());
        renbyAnimations.put(CharacterAnimation.RUN, Assets.getMainTextureAtlas().getRenbyNormal());
        renbyAnimations.put(CharacterAnimation.BLINK, Assets.getMainTextureAtlas().getRenbyBlink());

        characters = new Character[]{
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),

                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),

                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
                Character.builder()
                        .name("Lyze")
                        .preview(Assets.getMainTextureAtlas().getLyzeIdle().get(0))
                        .animations(lyzeAnimations)
                        .build(),

                Character.builder()
                        .name("Renby")
                        .preview(Assets.getMainTextureAtlas().getRenbyIdle().get(0))
                        .animations(renbyAnimations)
                        .build(),
        };
    }
}
