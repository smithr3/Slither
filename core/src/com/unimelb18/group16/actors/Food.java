package com.unimelb18.group16.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.unimelb18.group16.box2d.EnemyUserData;
import com.unimelb18.group16.enums.GameState;
import com.unimelb18.group16.utils.GameManager;

public class Food extends GameActor {

    private float stateTime;

    ShapeRenderer shapeRenderer;

    Camera camera;

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Food(Body body) {
        super(body);
        stateTime = 0f;
        shapeRenderer = new ShapeRenderer();
    }


    @Override
    public EnemyUserData getUserData() {
        return (EnemyUserData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (GameManager.getInstance().getGameState() != GameState.PAUSED) {
            stateTime += Gdx.graphics.getDeltaTime();
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.end();
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(body.getPosition().x, body.getPosition().y, 10);
        shapeRenderer.end();
        batch.begin();
    }

}