package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.map.Track;
import lombok.Getter;
import lombok.var;

import java.util.ArrayList;

public class MapSelectionMenuEntry extends Table {
    @Getter
    private final Track track;
    private final int index;

    private final Label name;

    private final ArrayList<Image> imagesToColor = new ArrayList<>();

    public MapSelectionMenuEntry(Track track, int index) {
        this.track = track;
        this.index = index;

        var stack = new Stack();
        var table = new Table();

        imagesToColor.add(new Image(Constants.assets.getMainTextureAtlas().getFrameMap()));
        stack.add(imagesToColor.get(imagesToColor.size() - 1));
        stack.add(new Image(track.getThumbnail()));
        imagesToColor.add(new Image(Constants.assets.getMainTextureAtlas().getPlateMap()));
        stack.add(imagesToColor.get(imagesToColor.size() - 1));

        table.add().growY().row();
        table.add(name = new Label(track.getName(), Constants.assets.getSkin())).padBottom(1).row();

        stack.add(table);

        add(stack).size(96, 96);
    }

    public void setFocus(int playerIndex) {
        var playerColor = Constants.playerColors[playerIndex];
        name.setColor(playerColor);

        for (Image image : imagesToColor)
            image.setColor(playerColor);

        addAction(Actions.sequence(
                Actions.moveBy(0, 8, 0.1f),
                Actions.moveBy(0, -8, 0.1f))
        );
    }

    public void unsetFocus() {
        name.setColor(Color.WHITE);

        for (Image image : imagesToColor)
            image.setColor(Color.WHITE);
    }
}
