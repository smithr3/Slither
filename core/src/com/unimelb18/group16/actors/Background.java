package com.unimelb18.group16.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unimelb18.group16.utils.Constants;
import com.unimelb18.group16.utils.ParallaxBackground;
import com.unimelb18.group16.utils.ParallaxLayer;

public class Background extends Actor {

    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 100;

    ParallaxBackground parallaxBackground;

    private Camera camera;

    public Background(Camera camera) {

        Texture imgTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        imgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        textureRegion = new TextureRegion(imgTexture);

        camera=camera;

        parallaxBackground = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(textureRegion,new Vector2(),new Vector2(0, 0)),
        }, 800, 480,new Vector2(150,0));

        textureRegion.setRegion(0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);

        textureRegionBounds1 = new Rectangle(0 - Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        textureRegionBounds2 = new Rectangle(Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    @Override
    public void act(float delta) {
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x - (delta * speed)) <= 0;
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x += delta * speed;
        textureRegionBounds2.x += delta * speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(Constants.APP_WIDTH, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

}