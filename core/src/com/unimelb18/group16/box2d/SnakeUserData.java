package com.unimelb18.group16.box2d;

import com.badlogic.gdx.math.Vector2;
import com.unimelb18.group16.enums.UserDataType;
import com.unimelb18.group16.utils.Constants;

public class SnakeUserData extends UserData {

    private final Vector2 runningPosition = new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y);
    private final Vector2 dodgePosition = new Vector2(Constants.RUNNER_DODGE_X, Constants.RUNNER_DODGE_Y);
    private Vector2 jumpingLinearImpulse;
    private Vector2 linearVelocity;

    public SnakeUserData(float width, float height) {
        super(width, height);
        jumpingLinearImpulse = Constants.RUNNER_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.SNAKE_HEAD;
        linearVelocity = Constants.ENEMY_LINEAR_VELOCITY;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public float getDodgeAngle() {
        // In radians
        return (float) (-90f * (Math.PI / 180f));
    }

    public Vector2 getRunningPosition() {
        return runningPosition;
    }

    public Vector2 getDodgePosition() {
        return dodgePosition;
    }

    public float getHitAngularImpulse() {
        return Constants.RUNNER_HIT_ANGULAR_IMPULSE;
    }
}