package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import lombok.Getter;
import lombok.var;

public class CharacterSelectPlayerMenuEntry extends Table {
    private final Label controllerName;
    private final Label playerName;
    private final Image thumbnail;

    @Getter
    private CharacterSelectMenuEntry character;
    @Getter
    private int playerIndex;
    @Getter
    private boolean finished;

    public CharacterSelectPlayerMenuEntry(int playerIndex) {
        this.playerIndex = playerIndex;

        setDebug(true);

        var stack = new Stack();

        var table = new Table();

        playerName = new Label("Player " + (playerIndex + 1), Constants.assets.getSkin());
        playerName.setColor(Constants.playerColors[playerIndex]);
        controllerName = new Label("???", Constants.assets.getSkin());
        controllerName.setColor(Constants.playerColors[playerIndex]);

        table.add().growY().row();
        table.add(playerName).padBottom(1).row();
        table.add(controllerName).padBottom(1).row();

        thumbnail = setupImage(Constants.assets.getMainTextureAtlas().getQuestionmark());
        var thumbnailBackground = setupImage(Constants.assets.getMainTextureAtlas().getFrame());
        var thumbnailForeground = setupImage(Constants.assets.getMainTextureAtlas().getPlate());

        stack.add(thumbnailBackground);
        stack.add(thumbnail);
        stack.add(thumbnailForeground);

        stack.add(table);

        add(stack).size(96, 96);
    }

    private Image setupImage(TextureRegion region) {
        var image = new Image(region);
        image.setColor(Constants.playerColors[playerIndex]);
        return image;
    }

    public void unsetPlayer() {
        if (character != null)
            character.unsetFocus(this);

        controllerName.setText("???");
        thumbnail.setColor(Constants.playerColors[playerIndex]);
        thumbnail.setDrawable(new TextureRegionDrawable(Constants.assets.getMainTextureAtlas().getQuestionmark()));
    }

    public void setPotentialCharacter(CharacterSelectMenuEntry character, VirtualGamepad gamepad) {
        this.character = character;

        character.setFocus(this);

        controllerName.setText(gamepad.getName().length() > 10 ? gamepad.getName().substring(0, 10) : gamepad.getName());

        thumbnail.setColor(Color.WHITE);
        thumbnail.setDrawable(new TextureRegionDrawable(character.getCharacter().getPreview()));
    }

    public void toggleFinishedSelection() {
        finished = !finished;
        thumbnail.setColor(finished ? Constants.playerColors[playerIndex] : Color.WHITE);
    }
}
