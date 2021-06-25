package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.characters.Character;
import dev.lyze.hamballracers.utils.Logger;
import lombok.Getter;

import java.util.ArrayList;

public class CharacterSelectMenuEntry extends Table {
    private static final Logger<CharacterSelectMenuEntry> logger = new Logger<>(CharacterSelectMenuEntry.class);

    @Getter
    private Character character;
    @Getter
    private int index;

    private final Label name;

    private final ArrayList<CharacterSelectPlayerMenuEntry> playerFocuses = new ArrayList<>();

    public CharacterSelectMenuEntry(Character character, int index) {
        this.character = character;
        this.index = index;

        add(new ImageButton(new TextureRegionDrawable(character.getPreview()))).row();

        name = new Label(character.getName(), Constants.assets.getSkin());
        add(name).row();
    }

    public void setFocus(CharacterSelectPlayerMenuEntry entry) {
        playerFocuses.add(entry);

        name.setColor(Constants.playerColors[entry.getPlayerIndex()]);
    }

    public void unsetFocus(CharacterSelectPlayerMenuEntry entry) {
        playerFocuses.remove(entry);

        if (playerFocuses.isEmpty())
            name.setColor(Color.WHITE);
        else
            name.setColor(Constants.playerColors[playerFocuses.get(playerFocuses.size() - 1).getPlayerIndex()]);
    }
}
