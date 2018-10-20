package com.unimelb18.group16.actors;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unimelb18.group16.utils.Constants;

public class MiniMap extends Actor {

    private ShapeRenderer shapeRenderer;

    private Vector2 snakeMapPosition;


    public MiniMap(Camera camera) {
        shapeRenderer = new ShapeRenderer();
        snakeMapPosition = new Vector2();

        snakeMapPosition.x = camera.position.x + 100;
        snakeMapPosition.y = camera.position.y + 100;
    }


    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(100, 100, 200, 200);
        shapeRenderer.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.circle(snakeMapPosition.x, snakeMapPosition.y, 10);
        shapeRenderer.end();


        batch.begin();


    }

    private float miniMapX;
    private float miniMapY;

    public void setSnakePosition(float x, float y) {

        miniMapX = x * 200 / Constants.APP_WIDTH;
        miniMapY = y * 200 / Constants.APP_HEIGHT;

        snakeMapPosition.x = miniMapX + 100;
        snakeMapPosition.y = miniMapY + 100;
    }

}
