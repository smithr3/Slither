package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SpeedButton extends Button {

    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;

    private ImageButton button;

    private Camera camera;

    ImageButton.ImageButtonStyle imageButtonStyle;

    private SpeedButton.SpeedButtonListener listener;

    public interface SpeedButtonListener {
        void onSpeed();

        void removeSpeed();
    }

    public SpeedButton(Camera camera, SpeedButton.SpeedButtonListener listener) {

        this.camera = camera;

        this.listener = listener;

        setWidth(80);
        setHeight(80);

        setPosition(camera.position.x + camera.viewportWidth * 2 / 5, camera.position.y - camera.viewportHeight * 2 / 5);

        myTexture = new Texture(Gdx.files.internal("speed_button.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        imageButtonStyle = new ImageButton.ImageButtonStyle();

        imageButtonStyle.up = myTexRegionDrawable;

        button = new ImageButton(myTexRegionDrawable); //Set the button up

        setStyle(imageButtonStyle);

        this.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                unTouched();
            }


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touched();
                return true;
            }
        });

    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//
//        batch.draw(speedTexture, camera.position.x + camera.viewportWidth * 2 / 5, camera.position.y - camera.viewportHeight * 2 / 5);
//
//
//    }

    public void touched() {
        listener.onSpeed();
    }

    public void unTouched() {
        listener.removeSpeed();
    }

}

