package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.map.Track;
import lombok.Getter;

public class MapSelectionMenuEntry extends Table {
    @Getter
    private final Track track;
    private final int index;

    private final Label name;

    public MapSelectionMenuEntry(Track track, int index) {
        this.track = track;
        this.index = index;

        add(new ImageButton(new TextureRegionDrawable(track.getThumbnail()))).size(128, 128).row();

        name = new Label(track.getName(), Constants.assets.getSkin());
        add(name).row();
    }

    public void setFocus(int playerIndex) {
        name.setColor(Constants.playerColors[playerIndex]);
    }

    public void unsetFocus() {
        name.setColor(Color.WHITE);
    }
}
