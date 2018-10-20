package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.unimelb18.group16.utils.Constants;

public class SettingsSaveButton extends GameButton {

    public interface SettingsSaveButtonListener {
        public void onSettingsSave();
    }

    private SettingsSaveButton.SettingsSaveButtonListener listener;


    public SettingsSaveButton(Rectangle bounds, SettingsSaveButton.SettingsSaveButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
       return Constants.CHANGE_SKIN_ASSETS_ID;
    }

    @Override
    public void touched() {
        listener.onSettingsSave();
    }


}
