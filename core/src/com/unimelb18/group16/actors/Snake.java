package com.unimelb18.group16.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;



public class Snake extends Actor {

    private Rectangle bounds;

    private float x;
    private float y;
    private double speed;
    private double angle;
    private double turnSpeed;

    private int headRadius;
    private int tailRadius;

    public Texture imgHead;

    public SnakeBody[] snakeBodies;

    private double targetAngle;

    public Snake(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);

        imgHead = new Texture("snake_head_right.png");

        x = bounds.getX() / 2;
        y = bounds.getY() / 2;

        speed = 7;
        angle = 0;
        turnSpeed = 0.07;

        headRadius = 30;
        tailRadius = 24;

        snakeBodies = new SnakeBody[10];

        for (int i = 0; i < 10; i++) {
            snakeBodies[i] = new SnakeBody(bounds, x-i*5, y);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // todo this draw actually has update code in it
        super.draw(batch, parentAlpha);

        angle = targetAngle; // todo remove when turn speed implemented correctly

        // no scaling used for FPS yet
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        x += dx;
        y += dy;

        // update snake body, by moving each segment towards the one in front
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                snakeBodies[i].moveTowards(x, y, speed);
            } else {
                snakeBodies[i].moveTowards(
                        snakeBodies[i-1].getX(),
                        snakeBodies[i-1].getY(),
                        speed
                );
            }
        }

        batch.draw(imgHead, 0, 0);
    }

    public void setNewHeading(double targetX, double targetY) {
        // get vector between current position and target point
        double dx = targetX - x;
        double dy = targetY - y;
        // find angle between that vector and the positive (right) horizontal axis
        // use atan2 so quadrants are handled correctly
        setTargetAngle(Math.atan2(dy, dx));


    }

    public double signedDifference(double a, double b) {
        // difference between two angles in radians
        // a is src, b is target
        double diff = a - b;
        diff = ( (diff + Math.PI) % 2*Math.PI ) - Math.PI;
        return diff;
    }

    private void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

}