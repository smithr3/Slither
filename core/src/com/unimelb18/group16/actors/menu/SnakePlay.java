package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unimelb18.group16.actors.Snake;
import com.unimelb18.group16.utils.AssetsManager;

public class SnakePlay extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    private Snake theSnake;

    private Vector2 position;
    private Vector2 lastPos;

    private double angle;

    private float speed;
    private double turnSpeed;
    private double targetAngle = 0;
    private float x;
    private float y;

    private int frames;

    public SnakePlay(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getLargeFont();

        x = bounds.getX() / 2;
        y = bounds.getY() / 2;

        position = new Vector2(x, y);
        lastPos = new Vector2(x, y);

        speed = 1;
        angle = 180;
        turnSpeed = 0.07;

        //Set inital speed and direction



        frames = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        angle = targetAngle;

        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        x += dx;
        y += dy;



//        theSnake.position.x += moveX * Gdx.graphics.getDeltaTime();
//        theSnake.position.y += moveY * Gdx.graphics.getDeltaTime();


//        Gdx.app.log("Snake X => ", theSnake.position.x + "");
//        Gdx.app.log("Snake Y => ", theSnake.position.y + "");
//
//        if (theSnake.position.x > 1024 + 32) theSnake.position.x = 0;
//        if (theSnake.position.x < 0 - 32) theSnake.position.x = 1024;
//        if (theSnake.position.y > 768 + 32) theSnake.position.y = 0;
//        if (theSnake.position.y < 0 - 32) theSnake.position.y = 768;

        int counter = 1;
        //   batch.begin();

//
//        batch.draw(theSnake.imgHead, theSnake.position.x, theSnake.position.y);
//        for (com.unimelb18.group16.actors.Body bodyPart : theSnake.body) {
//
//            if (counter == 1) {
//                moveTowards(theSnake.position.x, theSnake.position.y, counter, bodyPart);
//            } else {
//                moveTowards(theSnake.body.get(counter - 1).position.x, theSnake.body.get(counter - 1).position.y, counter, bodyPart);
//            }
//
//            batch.draw(bodyPart.imgBody, bodyPart.position.x, bodyPart.position.y);
//
//            counter++;
//        }

        // batch.end();
    }



    public void setNewHeading(double targetX, double targetY) {
        // get vector between current position and target point
        double dx = targetX - x;
        double dy = targetY - y;
        // find angle between that vector and the positive (right) horizontal axis
        // use atan2 so quadrants are handled correctly
        setTargetAngle(Math.atan2(dy, dx));
    }

    private void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

}