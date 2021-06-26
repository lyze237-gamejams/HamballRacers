package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.utils.Logger;
import lombok.Getter;
import lombok.var;

import java.util.ArrayList;

public class CharacterSelectMenuEntry extends Table {
    private static final Logger<CharacterSelectMenuEntry> logger = new Logger<>(CharacterSelectMenuEntry.class);

    @Getter
    private Character character;
    @Getter
    private int index;

    private final Label name;

    private final ArrayList<Image> imagesToColor = new ArrayList<>();

    private final ArrayList<CharacterSelectPlayerMenuEntry> playerFocuses = new ArrayList<>();

    public CharacterSelectMenuEntry(Character character, int index) {
        this.character = character;
        this.index = index;

        var stack = new Stack();
        var table = new Table();


        imagesToColor.add(new Image(Constants.assets.getMainTextureAtlas().getFrameCharacter()));
        stack.add(imagesToColor.get(imagesToColor.size() - 1));
        stack.add(new Image(character.getPreview()));
        imagesToColor.add(new Image(Constants.assets.getMainTextureAtlas().getPlateCharacter()));
        stack.add(imagesToColor.get(imagesToColor.size() - 1));

        name = new Label(character.getName(), Constants.assets.getSkin());

        table.add().growY().row();
        table.add(name).padBottom(1).row();

        stack.add(table);

        add(stack).size(96, 96);
    }

    public void setFocus(CharacterSelectPlayerMenuEntry entry) {
        playerFocuses.add(entry);

        name.setColor(Constants.playerColors[entry.getPlayerIndex()]);
        imagesToColor.forEach(i -> i.setColor(Constants.playerColors[entry.getPlayerIndex()]));

        addAction(Actions.sequence(
                Actions.moveBy(0, 8, 0.1f),
                Actions.moveBy(0, -8, 0.1f))
        );
    }

    public void unsetFocus(CharacterSelectPlayerMenuEntry entry) {
        playerFocuses.remove(entry);

        var newColor = Color.WHITE;
        if (!playerFocuses.isEmpty())
            newColor = Constants.playerColors[playerFocuses.get(playerFocuses.size() - 1).getPlayerIndex()];

        name.setColor(newColor);
        for (Image image : imagesToColor)
            image.setColor(newColor);
    }
}
