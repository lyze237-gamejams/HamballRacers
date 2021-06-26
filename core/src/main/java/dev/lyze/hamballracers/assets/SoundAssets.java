package dev.lyze.hamballracers.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import dev.lyze.hamballracers.assets.utils.DynamicAssets;
import dev.lyze.hamballracers.assets.utils.LoadAssetFromFile;
import lombok.Getter;

public class SoundAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("music/Kouzerumatsu-SAR-Theme.ogg")
    private Music themeSong;

    @Getter @LoadAssetFromFile("sounds/BallBump.ogg")
    private Sound ballbump;

    @Getter @LoadAssetFromFile("sounds/BallRollFast_LOOP.ogg")
    private Music ballRollFastLoop;

    @Getter @LoadAssetFromFile("sounds/BallRollslow_LOOP.ogg")
    private Music ballRollSlowLoop;

    @Getter @LoadAssetFromFile("sounds/CompleteLap.ogg")
    private Sound completeLap;

    @Getter @LoadAssetFromFile("sounds/CompleteRace.ogg")
    private Sound completeRace;

    @Getter @LoadAssetFromFile("sounds/Nitro.ogg")
    private Sound nitro;

    @Getter @LoadAssetFromFile("sounds/PadFast_LOOP.ogg")
    private Music padFast;

    @Getter @LoadAssetFromFile("sounds/PadRefillNitro_LOOP.ogg")
    private Music padRefillNitro;

    @Getter @LoadAssetFromFile("sounds/PadSlow_LOOP.ogg")
    private Music padSlow;

    @Getter @LoadAssetFromFile("sounds/UI_1.ogg")
    private Sound ui1;

    @Getter @LoadAssetFromFile("sounds/UI_2.ogg")
    private Sound ui2;

    @Getter @LoadAssetFromFile("sounds/UI_3.ogg")
    private Sound ui3;

    @Getter @LoadAssetFromFile("sounds/UI_GO.ogg")
    private Sound uiGo;

    @Getter @LoadAssetFromFile("sounds/UIClick.ogg")
    private Sound uiClick;

    @Override
    protected AssetManager generateAssMan() {
        return new AssetManager();
    }
}
