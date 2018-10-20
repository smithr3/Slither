package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class ArrowModeButton extends Button {
    protected Rectangle bounds;

    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    ImageButton.ImageButtonStyle imageButtonStyle;

    private Texture myTextureSelected;
    private TextureRegion myTextureRegionSelected;
    private TextureRegionDrawable myTexRegionDrawableSelected;

    private ArrowModeButtonListener listener;

    public interface ArrowModeButtonListener {
        void onArrowMode();
    }

    public ArrowModeButton(Rectangle bounds, ArrowModeButtonListener listener) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        this.listener = listener;
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        myTexture = new Texture(Gdx.files.internal("arrow_mode_blur.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        myTextureSelected = new Texture(Gdx.files.internal("arrow_mode.png"));
        myTextureRegionSelected = new TextureRegion(myTextureSelected);
        myTexRegionDrawableSelected = new TextureRegionDrawable(myTextureRegionSelected);

        button = new ImageButton(myTexRegionDrawable); //Set the button up

        imageButtonStyle = new ImageButton.ImageButtonStyle();

        imageButtonStyle.up = myTexRegionDrawable;
        imageButtonStyle.down = myTexRegionDrawableSelected;
        imageButtonStyle.checked = myTexRegionDrawableSelected;
        // button.setStyle(imageButtonStyle);
        button.setChecked(false);
        setStyle(imageButtonStyle);
        setDisabled(true);
        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touched();
                return true;
            }
        });
    }

    public void touched() {
        listener.onArrowMode();
    }

    public void select(boolean status) {
        setChecked(status);
    }
}
