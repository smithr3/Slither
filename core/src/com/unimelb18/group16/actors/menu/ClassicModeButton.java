package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.unimelb18.group16.utils.AssetsManager;


public class ClassicModeButton extends Button {
    protected Rectangle bounds;

    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;


    private Texture myTextureSelected;
    private TextureRegion myTextureRegionSelected;
    private TextureRegionDrawable myTexRegionDrawableSelected;



    private ImageTextButton button;
    ImageTextButton.ImageTextButtonStyle imageTextButtonStyle;

    private ClassicModeButton.ClassicModeButtonListener listener;

    public void select(boolean status) {
        setChecked(status);
    }

    public interface ClassicModeButtonListener {
        void onClassicMode();
    }

    public ClassicModeButton(Rectangle bounds, ClassicModeButtonListener listener) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        this.listener = listener;

        myTexture = new Texture(Gdx.files.internal("classic_mode_blur.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        myTextureSelected = new Texture(Gdx.files.internal("classic_mode.png"));
        myTextureRegionSelected = new TextureRegion(myTextureSelected);
        myTexRegionDrawableSelected = new TextureRegionDrawable(myTextureRegionSelected);


        imageTextButtonStyle = new ImageTextButton.ImageTextButtonStyle();

        imageTextButtonStyle.up = new TextureRegionDrawable(new TextureRegion(myTexture));
        imageTextButtonStyle.font = AssetsManager.getSmallFont();
        imageTextButtonStyle.fontColor = Color.BLACK;
        imageTextButtonStyle.checkedOffsetX = 50f;
        imageTextButtonStyle.down = myTexRegionDrawableSelected;
        imageTextButtonStyle.checked = myTexRegionDrawableSelected;
        button = new ImageTextButton("Classic Mode", imageTextButtonStyle); //Set the button up

        button.setOrigin(100, 100);
        // button.setStyle(imageButtonStyle);

        button.setChecked(false);
        setStyle(imageTextButtonStyle);
        setDisabled(true);;
        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touched();
                return true;
            }
        });
    }

    public void touched() {
        listener.onClassicMode();
    }
}
