package com.unimelb18.group16.box2d;

import com.badlogic.gdx.math.Vector2;
import com.unimelb18.group16.enums.UserDataType;
import com.unimelb18.group16.utils.Constants;

public class PowerFoodData extends UserData {

    private Vector2 linearVelocity;

    public int getFoodType() {
        return foodType;
    }

    private int foodType;

    public PowerFoodData(float width, float height, int foodType) {
        super(width, height);
        userDataType = UserDataType.ENEMY;
        linearVelocity = Constants.ENEMY_LINEAR_VELOCITY;
        this.foodType = foodType;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }
}