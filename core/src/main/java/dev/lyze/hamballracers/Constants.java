package dev.lyze.hamballracers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import dev.lyze.hamballracers.assets.MainAssets;
import dev.lyze.hamballracers.assets.SoundAssets;
import dev.lyze.hamballracers.eventSystem.EventManager;
import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.screens.level.characters.CharacterAnimation;
import dev.lyze.hamballracers.screens.level.map.Track;
import dev.lyze.hamballracers.utils.input.PlayersVirtualGamepadMapping;
import lombok.var;

import java.util.HashMap;

public class Constants {
    public static boolean debug = false;
    public static MainAssets assets = new MainAssets();
    public static SoundAssets sounds = new SoundAssets();

    public static EventManager eventManager = new EventManager();

    public static PlayersVirtualGamepadMapping gamepadMapping;
    public static Character[] characters;

    public static Track[] tracks;

    public static final Color[] playerColors = new Color[] { Color.CYAN, Color.ORANGE, Color.GREEN, Color.YELLOW };
    public static final int maxPlayers = playerColors.length;
    public static final TextureRegionDrawable[] playerBackgrounds = new TextureRegionDrawable[Constants.maxPlayers];

    public static void initialize() {
        Constants.assets.load();
        Constants.assets.finishAndConsume();

        Constants.sounds.load();
        Constants.sounds.finishAndConsume();

        gamepadMapping = new PlayersVirtualGamepadMapping();

        initializeCharacterColors();
        initializeCharacters();
        initializeTracks();
    }

    private static void initializeTracks() {
        tracks = new Track[] {
                Track.builder()
                        .name("Desert")
                        .thumbnail(Constants.assets.getMapTextureAtlas().getDesert())
                        .map(Constants.assets.getDesertMap())
                        .build(),

                Track.builder()
                        .name("Basic")
                        .thumbnail(Constants.assets.getMapTextureAtlas().getBasicmap())
                        .map(Constants.assets.getBasicMap())
                        .build(),

                Track.builder()
                        .name("Desert")
                        .thumbnail(Constants.assets.getMapTextureAtlas().getDesert())
                        .map(Constants.assets.getDesertMap())
                        .build(),

                Track.builder()
                        .name("Basic")
                        .thumbnail(Constants.assets.getMapTextureAtlas().getBasicmap())
                        .map(Constants.assets.getBasicMap())
                        .build(),
        };
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
        lyzeAnimations.put(CharacterAnimation.NORMAL, assets.getMainTextureAtlas().getLyzeNormal());
        lyzeAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getLyzeBlink());

        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> renbyAnimations = new HashMap<>();
        renbyAnimations.put(CharacterAnimation.IDLE, assets.getMainTextureAtlas().getRenbyIdle());
        renbyAnimations.put(CharacterAnimation.NORMAL, assets.getMainTextureAtlas().getRenbyNormal());
        renbyAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getRenbyBlink());

        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> jakeAnimations = new HashMap<>();
        jakeAnimations.put(CharacterAnimation.IDLE, assets.getMainTextureAtlas().getJakeIdle());
        jakeAnimations.put(CharacterAnimation.NORMAL, assets.getMainTextureAtlas().getJakeNormal());
        jakeAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getJakeBlink());

        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> zootyAnimations = new HashMap<>();
        zootyAnimations.put(CharacterAnimation.IDLE, assets.getMainTextureAtlas().getZootyIdle());
        zootyAnimations.put(CharacterAnimation.NORMAL, assets.getMainTextureAtlas().getZootyNormal());
        zootyAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getZootyBlink());

        HashMap<CharacterAnimation, Array<TextureAtlas.AtlasRegion>> breeAnimations = new HashMap<>();
        breeAnimations.put(CharacterAnimation.IDLE, assets.getMainTextureAtlas().getBreeIdle());
        breeAnimations.put(CharacterAnimation.NORMAL, assets.getMainTextureAtlas().getBreeNormal());
        breeAnimations.put(CharacterAnimation.BLINK, assets.getMainTextureAtlas().getBreeBlink());

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
                        .name("Jake")
                        .preview(assets.getMainTextureAtlas().getJakeIdle().get(0))
                        .animations(jakeAnimations)
                        .build(),

                Character.builder()
                        .name("Zooty")
                        .preview(assets.getMainTextureAtlas().getZootyIdle().get(0))
                        .animations(zootyAnimations)
                        .build(),

                Character.builder()
                        .name("Bree")
                        .preview(assets.getMainTextureAtlas().getBreeIdle().get(0))
                        .animations(breeAnimations)
                        .build()
        };
    }
}
