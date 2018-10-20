package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.unimelb18.group16.box2d.EnemyUserData;
import com.unimelb18.group16.box2d.PowerFoodData;
import com.unimelb18.group16.box2d.RunnerUserData;

public class PowerFood extends GameActor {

    private float stateTime;

    Camera camera;

    public Texture powerFoodTexture;

    private String powerFoodTypes[] = {"snake_big_food.png", "snake_speed_food.png"};

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public PowerFood(Body body, int foodType) {
        super(body);
        stateTime = 0f;

        if (foodType > 1) {
            foodType = 1;
        }

        powerFoodTexture = new Texture(powerFoodTypes[foodType]);

    }


    @Override
    public PowerFoodData getUserData() {
        return (PowerFoodData) userData;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(powerFoodTexture, body.getPosition().x, body.getPosition().y);
    }

}