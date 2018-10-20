package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.unimelb18.group16.utils.Constants;

public class ChangeSkinSaveButton extends GameButton {



    public interface ChangeSkinSaveButtonListener {
        public void onChangeSkinSave();
    }

    private ChangeSkinSaveButton.ChangeSkinSaveButtonListener listener;


    public ChangeSkinSaveButton(Rectangle bounds, ChangeSkinSaveButton.ChangeSkinSaveButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
       return Constants.SAVE_BUTTON;
    }

    @Override
    public void touched() {
        listener.onChangeSkinSave();
    }


}
