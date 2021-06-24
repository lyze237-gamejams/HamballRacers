package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import lombok.var;

public class CharacterSelectMenu extends ManagedScreenAdapter {

    private GameType gameType;

    private Stage stage;

    public CharacterSelectMenu() {
        stage = new Stage(new FitViewport(640, 360));

        var root = new Table();
        root.setFillParent(true);

        root.add(setupCharacterSelection()).grow().row();
        root.add(setupButtons()).growX();

        stage.addActor(root);
    }

    private Table setupCharacterSelection() {
        var table = new Table();
        table.defaults().pad(2);

        for (int i = 0; i < Constants.characters.length; i++) {
            var character = Constants.characters[i];

            var characterTable = new Table();
            characterTable.add(new ImageButton(new TextureRegionDrawable(character.getPreview()))).row();
            characterTable.add(new Label(character.getName(), Constants.Assets.getSkin()));
            table.add(characterTable);

            if (i % 10 == 0 && i > 0)
                table.row();
        }
        
        return table;
    }

    private Table setupButtons() {
        var table = new Table();

        table.add(new TextButton("Back", Constants.Assets.getSkin(), "tinyButton"));
        table.add().growX();
        table.add(new TextButton("Ok", Constants.Assets.getSkin(), "tinyButton"));
        
        return table;
    }

    @Override
    public void show() {
        super.show();

        gameType = (GameType) pushParams[0];
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public Color getClearColor() {
        return Color.TEAL;
    }
}
