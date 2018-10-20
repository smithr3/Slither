package com.unimelb18.group16.box2d;

import com.badlogic.gdx.math.Vector2;
import com.unimelb18.group16.enums.UserDataType;
import com.unimelb18.group16.utils.Constants;

public class PowerFoodData extends UserData {

    public int getFoodType() {
        return foodType;
    }

    private int foodType;

    public PowerFoodData(float width, float height, int foodType) {
        super(width, height);
        userDataType = UserDataType.POWER_FOOD;
        this.foodType = foodType;
    }


}