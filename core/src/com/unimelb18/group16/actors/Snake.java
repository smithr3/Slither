package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.unimelb18.group16.box2d.SnakeUserData;
import com.unimelb18.group16.utils.SharedData;

import java.util.ArrayList;

public class Snake extends GameActor {

    private Rectangle bounds;

    private float x;
    private float y;

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    private double speed;
    private double angle;
    private double turnSpeed;

    private double distance;

    private Color snakeColor[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK, Color.GRAY, Color.GREEN};
    private int currentColor = 0;

    private int headRadius;
    private int tailRadius;


    public Texture snakeHead;
    public Texture snakeBody;

    Vector2 position;
    Vector2 velocity;
    Vector2 movement;

    boolean angleChange = false;

    Vector2 touch;
    Vector2 dir;

    TextureRegion snakeRegion;

    public boolean isBoosting() {
        return boosting;
    }

    public void setBoosting(boolean boosting) {
        this.boosting = boosting;
    }

    private boolean boosting;


    public void removeSnakeBody() {
        snakeBodies.remove(snakeBodies.size() - 1);
    }

    public void addSnakeBody() {

        SnakeBody snakeBody = new SnakeBody(this.snakeBodies.get(this.snakeBodies.size() - 1).getX(), this.snakeBodies.get(this.snakeBodies.size() - 1).getY(), "skin_1.png");


        this.snakeBodies.add(snakeBody);
    }

    public ArrayList<SnakeBody> snakeBodies;

    private double targetAngle;

    Camera camera;

    ShapeRenderer shapeRenderer;

    public Snake(Body body) {
        super(body);
        //setWidth(bounds.width);
        // setHeight(bounds.height);

        // this.camera=camera;
        position = new Vector2();
        velocity = new Vector2();
        movement = new Vector2();
        touch = new Vector2();
        dir = new Vector2();

        snakeHead = new Texture("worm_eyes.png");
        snakeBody = new Texture("skin_2.png");

        snakeRegion = new TextureRegion(snakeHead);
        snakeRegion.setRegion(0, 0, snakeHead.getWidth(), snakeHead.getHeight());

        x = body.getPosition().x;
        y = body.getPosition().y;

        speed = 200;
        angle = 0;
        turnSpeed = 0.07;

        headRadius = 30;
        tailRadius = 24;

        snakeBodies = new ArrayList<SnakeBody>();

        shapeRenderer = new ShapeRenderer();

        String currentSkin = SharedData.getKey("currentSkin");

        if (currentSkin != null && !currentSkin.equals("")) {
            currentColor = Integer.parseInt(currentSkin);
        }

        for (int i = 0; i < 5; i++) {
            snakeBodies.add(new SnakeBody(x, y, "skin_1.png"));
        }

        position.set(x, y);
    }

    @Override
    public SnakeUserData getUserData() {
        return (SnakeUserData) userData;
    }

    Vector2 velocity4;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //dir.set(touch).sub(position).nor();
        //velocity.set(dir).scl(4);
        //movement.set(velocity).scl(0.4f);
//        if (position.dst2(touch) > movement.len2()) {
//            position.add(movement);
//        } else {
//
//            if (angleChange) {
//                angle = position.angleRad(touch);
//                angleChange = false;
//
//                double dx = touch.x - x;
//                double dy = touch.y - y;
//                // find angle between that vector and the positive (right1) horizontal axis
//                // use atan2 so quadrants are handled correctly
//                angle = Math.atan2(dy, dx);
//            }
//
//            double dx = Math.cos(angle);
//            double dy = Math.sin(angle);
//            x += dx * 40;
//            y += dy * 40;
//            touch.x = x;
//            touch.y = y;
//        }

//        position.add(movement);
//
//        if (angleChange) {
//            angle = position.angleRad(touch);
//            angleChange = false;
//
//            double dx = touch.x - x;
//            double dy = touch.y - y;
//            // find angle between that vector and the positive (right1) horizontal axis
//            // use atan2 so quadrants are handled correctly
//            angle = Math.atan2(dy, dx);
//        }
//
//        double dx = Math.cos(angle);
//        double dy = Math.sin(angle);
//        x += dx * 2;
//        y += dy * 2;
//        touch.x = x;
//        touch.y = y;
//
//        double fx = .25f*Math.cos(Math.toRadians(angle));
//        double fy = .25f*Math.sin(Math.toRadians(angle));
//        body.applyLinearImpulse((float)fx, (float)fy, this.body.getPosition().x,this.body.getPosition().y,false);
//        body.setLinearVelocity((float)fx, (float)fy);
//
//        x = body.getPosition().x;
//        y = body.getPosition().y;

        //  batch.end();


        if (body.getPosition().sub(touch).len() < 3) {

            if (angleChange) {
                angle = body.getPosition().angleRad(touch);
                angleChange = false;
                double dx = touch.x - x;
                double dy = touch.y - y;
                // find angle between that vector and the positive (right1) horizontal axis
                // use atan2 so quadrants are handled correctly
                angle = Math.atan2(dy, dx);

                if (angle > 0) {
                    snakeRegion.flip(true, false);
                } else {
                    snakeRegion.flip(false, false);
                }
            }

            double dx = Math.cos(angle);
            double dy = Math.sin(angle);
            x += dx * speed / 5;
            y += dy * speed / 5;
            touch.x = x;
            touch.y = y;
        }


        velocity = new Vector2(touch);
        velocity4 = velocity.sub(body.getPosition());
        velocity4.nor();


        if (boosting && snakeBodies.size() > 5) {
            speed = 400;
        } else {
            speed = 200;
        }

        velocity4.scl((float) speed);
        body.setLinearVelocity(velocity4);

        x = body.getPosition().x;
        y = body.getPosition().y;

        // batch.begin();
        for (int i = 0; i < snakeBodies.size(); i++) {
            if (i == 0) {
                snakeBodies.get(i).moveTowards(x, y, 5);
            } else {
                snakeBodies.get(i).moveTowards(snakeBodies.get(i - 1).getX(), snakeBodies.get(i - 1).getY(), 5);
            }

            batch.draw(snakeBodies.get(i).getSnakeBody(), snakeBodies.get(i).getX(), snakeBodies.get(i).getY());
        }


        // body.setTransform(x, y, 0);
        batch.draw(snakeRegion, x, y);
    }

    public void setNewHeading(float targetX, float targetY) {
        // get vector between current position and target point
//        double dx = targetX - x;
//        double dy = targetY - y;
//        // find angle between that vector and the positive (right1) horizontal axis
//        // use atan2 so quadrants are handled correctly
//        setTargetAngle(Math.atan2(dy, dx));
        touch.set(targetX, targetY);
        angleChange = true;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

}