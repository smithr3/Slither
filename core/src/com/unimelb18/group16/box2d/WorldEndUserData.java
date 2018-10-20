package com.unimelb18.group16.box2d;

import com.unimelb18.group16.enums.UserDataType;

public class WorldEndUserData extends UserData {

    public WorldEndUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.WORLD_END;
    }

}