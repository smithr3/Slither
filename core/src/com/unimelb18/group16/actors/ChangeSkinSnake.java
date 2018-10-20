package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unimelb18.group16.utils.SharedData;

import java.util.ArrayList;

public class ChangeSkinSnake extends Actor {

    private Rectangle bounds;

    private float x;
    private float y;

    private String snakeColor[] = {"skin_1.png", "skin_2.png", "skin_3.png", "skin_4.png"};

    private String currentSkin;

    private ShapeRenderer shapeRenderer;

    private int currentColor = 0;

    public ArrayList<SnakeBody> snakeBodies;

    public Texture snakeHead;
    public Texture snakeBody;

    public ChangeSkinSnake(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);

        x = bounds.getX() / 2;
        y = bounds.getY() / 2;

        snakeBodies = new ArrayList<SnakeBody>();

        String currentSkin = SharedData.getKey("currentSkin");

        if (currentSkin != null && !currentSkin.equals("")) {
            currentColor = Integer.parseInt(currentSkin);
        }

        snakeHead = new Texture("worm_eyes.png");
        snakeBody = new Texture(snakeColor[currentColor]);

        for (int i = 0; i < 5; i++) {
            snakeBodies.add(new SnakeBody(x, y, snakeColor[currentColor]));
        }

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        batch.draw(snakeHead, x - snakeBodies.size() *2, y);

        for (int i = 0; i < snakeBodies.size(); i++) {
            batch.draw(snakeBodies.get(i).getSnakeBody(), x + 30 * i, y);
        }
    }

    public int getCurrentColor() {
        return currentColor;
    }


    public void changeColor(int side) {
        switch (side) {
            case 1:
                currentColor--;
                if (currentColor < 0) {
                    currentColor = snakeColor.length - 1;
                }

                for (int i = 0; i < 5; i++) {
                    snakeBodies.get(i).setSnakeBodyTexture(snakeColor[currentColor]);
                }

                break;
            case 2:
                currentColor++;
                if (currentColor >= snakeColor.length) {
                    currentColor = 0;
                }

                for (int i = 0; i < 5; i++) {
                    snakeBodies.get(i).setSnakeBodyTexture(snakeColor[currentColor]);
                }
                break;
        }

    }
}
