package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public class HamsterBallInput {

    private static final int[][] playerControls = new int[][] {
            new int[] { Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Input.Keys.SHIFT_LEFT },
            new int[] { Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.SHIFT_RIGHT }
    };

    private final HamsterBall hamsterBall;

    @Getter
    private final Vector2 inputVelocity = new Vector2();
    @Getter
    private boolean usingNitro;

    public HamsterBallInput(HamsterBall hamsterBall) {
        this.hamsterBall = hamsterBall;
    }

    public void update(float delta) {
        readInputVelocity();
        usingNitro = Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][4]);
    }

    public void readInputVelocity() {
        inputVelocity.set(0, 0);

        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][0]))
            inputVelocity.y = 1;
        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][1]))
            inputVelocity.y = -1;

        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][2]))
            inputVelocity.x = -1;
        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][3]))
            inputVelocity.x = 1;
    }
}
