package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class HamsterBallInput {
    private final Vector2 inputVelocity = new Vector2();

    private static final int[][] playerControls = new int[][] {
            new int[] { Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D },
            new int[] { Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT }
    };

    private final HamsterBall hamsterBall;

    public HamsterBallInput(HamsterBall hamsterBall) {
        this.hamsterBall = hamsterBall;
    }

    public void update(float delta) {

    }

    public Vector2 readInputVelocity() {
        inputVelocity.set(0, 0);

        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][0]))
            inputVelocity.y = 1;
        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][1]))
            inputVelocity.y = -1;

        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][2]))
            inputVelocity.x = -1;
        if (Gdx.input.isKeyPressed(playerControls[hamsterBall.getPlayerIndex()][3]))
            inputVelocity.x = 1;

        return inputVelocity;
    }
}
