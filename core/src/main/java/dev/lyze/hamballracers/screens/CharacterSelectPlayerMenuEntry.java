package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

        var stack = new Stack();

        var table = new Table();

        playerName = new Label("Player " + (playerIndex + 1), Constants.assets.getSkin());
        playerName.setColor(Constants.playerColors[playerIndex]);
        controllerName = new Label("???", Constants.assets.getSkin());
        controllerName.setColor(Constants.playerColors[playerIndex]);

        table.add().growY().row();
        table.add(playerName).padBottom(1).row();
        table.add(controllerName).padBottom(1).row();

        var thumbnailBackground = setupImage(Constants.assets.getMainTextureAtlas().getFramePlayer());
        var thumbnailForeground = setupImage(Constants.assets.getMainTextureAtlas().getPlatePlayer());

        stack.add(thumbnailBackground);
        stack.add(thumbnail = new Image(Constants.assets.getMainTextureAtlas().getQuestionmark()));
        thumbnail.getColor().a = 0.2f;
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
        thumbnail.setDrawable(new TextureRegionDrawable(Constants.assets.getMainTextureAtlas().getQuestionmark()));
    }

    public void setPotentialCharacter(CharacterSelectMenuEntry character, VirtualGamepad gamepad) {
        this.character = character;

        character.setFocus(this);

        controllerName.setText(gamepad.getName().length() > 10 ? gamepad.getName().substring(0, 10) : gamepad.getName());

        thumbnail.addAction(Actions.sequence(
                Actions.fadeOut( 0.1f),
                Actions.run(() -> thumbnail.setDrawable(new TextureRegionDrawable(character.getCharacter().getPreview()))),
                Actions.alpha(0.2f, 0.1f)
        ));
    }

    public void toggleFinishedSelection() {
        finished = !finished;
        thumbnail.addAction(Actions.alpha(finished ? 1 : 0.2f, 0.15f));
    }
}
