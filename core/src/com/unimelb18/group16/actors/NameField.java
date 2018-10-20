package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.unimelb18.group16.utils.AssetsManager;

public class NameField extends Actor {
    TextField playerName;

    Camera camera;

    public NameField(Camera camera) {
        this.camera = camera;
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = AssetsManager.getSmallestFont();
        setWidth(100);
        setHeight(100);
        playerName = new TextField("ENTER YOUR NAME", textFieldStyle);
        playerName.setPosition(camera.viewportWidth / 2, camera.viewportHeight / 10);
        playerName.setSize(88, 14);
    }

}
