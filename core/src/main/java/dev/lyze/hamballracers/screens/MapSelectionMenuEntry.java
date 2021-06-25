package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.map.Track;

public class MapSelectionMenuEntry extends Table {
    private Track track;
    private int index;

    public MapSelectionMenuEntry(Track track, int index) {
        this.track = track;
        this.index = index;

        add(new ImageButton(new TextureRegionDrawable(track.getThumbnail()))).size(128, 128).row();
        add(new Label(track.getName(), Constants.assets.getSkin()));
    }
}
