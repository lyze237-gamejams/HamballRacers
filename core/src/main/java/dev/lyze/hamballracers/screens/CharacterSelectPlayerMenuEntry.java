package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import lombok.Getter;

public class CharacterSelectPlayerMenuEntry extends Table {
    private final Label controllerName;
    private final Label playerName;
    private final Image thumbnail;

    @Getter
    private String guid;
    @Getter
    private CharacterSelectMenuEntry character;
    @Getter
    private int playerIndex;
    @Getter
    private boolean finished;

    public CharacterSelectPlayerMenuEntry(String guid, int playerIndex) {
        this.guid = guid;
        this.playerIndex = playerIndex;

        playerName = new Label("Player " + (playerIndex + 1), Constants.assets.getSkin());
        playerName.setColor(Constants.playerColors[playerIndex]);
        controllerName = new Label("???", Constants.assets.getSkin());
        controllerName.setColor(Constants.playerColors[playerIndex]);

        thumbnail = new Image(Constants.assets.getMainTextureAtlas().getQuestionmark());
        thumbnail.setColor(Constants.playerColors[playerIndex]);

        add(playerName).row();
        add(controllerName).row();
        add(thumbnail).size(96, 96);
    }

    public void setGamepad(String guid) {
        this.guid = guid;
    }

    public void unsetPlayer() {
        guid = null;

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
