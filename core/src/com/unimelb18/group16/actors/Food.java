package com.unimelb18.group16.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.unimelb18.group16.box2d.EnemyUserData;
import com.unimelb18.group16.box2d.RunnerUserData;
import com.unimelb18.group16.enums.GameState;
import com.unimelb18.group16.utils.AssetsManager;
import com.unimelb18.group16.utils.Constants;
import com.unimelb18.group16.utils.GameManager;

public class Food extends GameActor {

    private float stateTime;

    private TextureRegion jumpingTexture;

    ShapeRenderer shapeRenderer;

    Camera camera;

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Food(Body body) {
        super(body);
        stateTime = 0f;
        shapeRenderer = new ShapeRenderer();

        jumpingTexture = AssetsManager.getTextureRegion(Constants.RUNNER_JUMPING_ASSETS_ID);
    }


    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (GameManager.getInstance().getGameState() != GameState.PAUSED) {
            stateTime += Gdx.graphics.getDeltaTime();
        }

//        batch.draw(jumpingTexture, body.getPosition().x, body.getPosition().y);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.end();
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(body.getPosition().x, body.getPosition().y, 10);
        shapeRenderer.end();
        batch.begin();

//        batch.draw((TextureRegion) animation.getKeyFrame(stateTime, true), (screenRectangle.x - (screenRectangle.width * 0.1f)),
//                screenRectangle.y, screenRectangle.width * 1.2f, screenRectangle.height * 1.1f);
    }

}