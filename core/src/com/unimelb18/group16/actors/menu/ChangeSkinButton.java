package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.unimelb18.group16.utils.Constants;

public class ChangeSkinButton extends GameButton {

    public interface ChangeSkinButtonListener {
        public void onChangeSkin();
    }

    private ChangeSkinButton.ChangeSkinButtonListener listener;


    public ChangeSkinButton(Rectangle bounds, ChangeSkinButton.ChangeSkinButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
       return Constants.CHANGE_SKIN_REGION_NAME;
    }

    @Override
    public void touched() {
        listener.onChangeSkin();
    }
}
