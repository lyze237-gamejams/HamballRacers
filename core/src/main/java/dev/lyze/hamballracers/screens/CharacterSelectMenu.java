package dev.lyze.hamballracers.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.eskalon.commons.screen.transition.impl.PushTransition;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.screens.level.Player;
import dev.lyze.hamballracers.utils.Logger;
import dev.lyze.hamballracers.utils.ManagedScreenAdapter;
import dev.lyze.hamballracers.utils.input.PlayerInputListener;
import dev.lyze.hamballracers.utils.input.VirtualGamepad;
import dev.lyze.hamballracers.utils.input.VirtualGamepadButton;
import lombok.var;

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterSelectMenu extends ManagedScreenAdapter implements PlayerInputListener {
    private static final Logger<CharacterSelectMenu> logger = new Logger<>(CharacterSelectMenu.class);

    private static final int charactersPerRow = 8;

    private final Stage stage;

    private final ArrayList<CharacterSelectMenuEntry> characters = new ArrayList<>();
    private final CharacterSelectPlayerMenuEntry[] players = new CharacterSelectPlayerMenuEntry[Constants.maxPlayers];

    private boolean processInput = false;

    public CharacterSelectMenu() {
        Constants.gamepadMapping.addListener(this);

        stage = new Stage(new ExtendViewport(640, 360));

        var root = new Table();
        root.setFillParent(true);

        root.add(new Label("Character Selection", Constants.assets.getSkin(), "characterSelectTitle")).pad(12).row();
        root.add(setupCharacterSelection()).grow().row();
        root.add(setupPlayers()).pad(12).growX();

        stage.addActor(root);
    }

    private Table setupCharacterSelection() {
        var table = new Table();
        table.defaults().pad(2);

        for (int i = 0; i < Constants.characters.length; i++) {
            var character = Constants.characters[i];

            var entry = new CharacterSelectMenuEntry(character, i);
            characters.add(entry);
            table.add(entry);

            if (i % (charactersPerRow - 1) == 0 && i > 0)
                table.row();
        }

        return table;
    }

    private Table setupPlayers() {
        var table = new Table();

        for (int i = 0; i < players.length; i++) {
            players[i] = new CharacterSelectPlayerMenuEntry(null, i);
            table.add(players[i]).growX();
        }

        return table;
    }

    @Override
    public void show() {
        super.show();

        processInput = true;

        for (var player : players) {
            if (player == null)
                continue;

            if (player.isFinished())
                player.toggleFinishedSelection();
        }
    }

    @Override
    public void hide() {
        processInput = false;
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

    @Override
    public void onDeregistered(VirtualGamepad gamepad, int index, boolean disconnected) {
        var player = players[index];
        player.unsetPlayer();
    }

    @Override
    public void onRegistered(VirtualGamepad gamepad, int index) {
        var player = players[index];
        player.setGamepad(gamepad.getGuid());

        changeFocus(player, 0, gamepad);
    }

    @Override
    public void onButtonDown(VirtualGamepad gamepad, VirtualGamepadButton button, int index) {
        if (!processInput)
            return;

        var player = players[index];

        if (button == VirtualGamepadButton.LEFT)
            changeFocus(player, player.getCharacter().getIndex() - 1, gamepad);
        else if (button == VirtualGamepadButton.RIGHT)
            changeFocus(player, player.getCharacter().getIndex() + 1, gamepad);
        else if (button == VirtualGamepadButton.DOWN)
            changeFocus(player, player.getCharacter().getIndex() + charactersPerRow, gamepad);
        else if (button == VirtualGamepadButton.UP)
            changeFocus(player, player.getCharacter().getIndex() - charactersPerRow, gamepad);
        else if (button == VirtualGamepadButton.OK) {
            player.toggleFinishedSelection();

            if (Arrays.stream(players).filter(p -> p.getGuid() != null).allMatch(CharacterSelectPlayerMenuEntry::isFinished))
                transitionToGameScreen();
        }
    }

    private void transitionToGameScreen() {
        if (game.getScreenManager().inTransition())
            return;

        Player[] gamePlayers = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            if (players[i].getGuid() != null) {
                gamePlayers[i] = new Player(Constants.gamepadMapping.getGamepad(players[i].getGuid()), players[i].getCharacter().getCharacter(), i);
            }
        }

        game.getScreenManager().pushScreen(MapSelectionMenu.class.getName(), PushTransition.class.getName(), (Object[]) gamePlayers);
    }

    @Override
    public void onButtonUp(VirtualGamepad gamepad, VirtualGamepadButton button, int index) { }

    private void changeFocus(CharacterSelectPlayerMenuEntry player, int newIndex, VirtualGamepad gamepad) {
        if (player.isFinished())
            return;

        if (player.getCharacter() != null)
            characters.get(player.getCharacter().getIndex()).unsetFocus(player);

        if (newIndex < 0)
            newIndex = 0;
        else if (newIndex >= characters.size())
            newIndex = characters.size() - 1;

        player.setPotentialCharacter(characters.get(newIndex), gamepad);
    }
}
