package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.unimelb18.group16.box2d.SnakeUserData;

import java.util.ArrayList;
import java.util.UUID;

public class Snake extends GameActor {

    private Rectangle bounds;

    private float x;
    private float y;

    @Override
    public float getX() {
        return x;
    }

    public String getSnakeName() {
        return snakeName;
    }

    private String snakeName;

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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double speed;
    private double angle;
    private double turnSpeed;

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    private int defaultSpeed;

    private boolean stopMovement = false;

    private double distance;

    private String snakeColor[] = {"skin_1.png", "skin_2.png", "skin_3.png", "skin_4.png"};
    private int currentColor = 0;

    private int headRadius;
    private int tailRadius;

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    private String uniqueIdentifier;


    public Texture snakeHead;
    public Texture snakeBody;

    Vector2 position;
    Vector2 velocity;
    Vector2 movement;

    boolean angleChange = false;

    Vector2 touch;
    Vector2 dir;

    int[] receivedPowerUp;


    TextureRegion snakeRegion;

    public boolean isBoosting() {
        return boosting;
    }

    public void setBoosting(boolean boosting) {
        this.boosting = boosting;
    }

    private boolean boosting;


    public void removeSnakeBody() {
        if (snakeBodies.size() > 5) {
            snakeBodies.remove(snakeBodies.size() - 1);
        }

    }

    public void addSnakeBody() {

        SnakeBody snakeBody = new SnakeBody(this.snakeBodies.get(this.snakeBodies.size() - 1).getX(), this.snakeBodies.get(this.snakeBodies.size() - 1).getY(), snakeColor[currentColor]);


        this.snakeBodies.add(snakeBody);
    }

    public ArrayList<SnakeBody> snakeBodies;

    private double targetAngle;

    Camera camera;

    ShapeRenderer shapeRenderer;

    public int[] getReceivedPowerUp() {
        return receivedPowerUp;
    }

    public Snake(Body body, String name, int currentColor, String uniqueName, int size) {
        super(body);

        this.snakeName = name;

        position = new Vector2();
        velocity = new Vector2();
        movement = new Vector2();
        touch = new Vector2();
        dir = new Vector2();

        if (uniqueName.equals("")) {
            uniqueIdentifier = UUID.randomUUID().toString();
        } else {
            uniqueIdentifier = uniqueName;
        }


        stopMovement = false;

        receivedPowerUp = new int[5];

        for (int k = 0; k < 5; k++) {
            receivedPowerUp[k] = -1;
        }

        this.currentColor = currentColor;

        if (this.currentColor > 4 || this.currentColor < 0) {
            this.currentColor = 0;
        }

        snakeHead = new Texture("worm_eyes.png");
        snakeBody = new Texture(snakeColor[this.currentColor]);

        snakeRegion = new TextureRegion(snakeHead);
        snakeRegion.setRegion(0, 0, snakeHead.getWidth(), snakeHead.getHeight());

        x = body.getPosition().x;
        y = body.getPosition().y;

        defaultSpeed = 200;

        speed = defaultSpeed;
        angle = 0;
        turnSpeed = 0.07;

        headRadius = 30;
        tailRadius = 24;

        snakeBodies = new ArrayList<SnakeBody>();

        shapeRenderer = new ShapeRenderer();

        if (size < 5) {
            size = 5;
        }


        for (int i = 0; i < size; i++) {
            snakeBodies.add(new SnakeBody(x, y, snakeColor[this.currentColor]));
        }

        position.set(x, y);
    }


    public void addReceivedPower(int type) {
        for (int i = 0; i < 5; i++) {
            if (receivedPowerUp[i] == -1) {
                receivedPowerUp[i] = type;
                break;
            }
        }
    }


    @Override
    public SnakeUserData getUserData() {
        return (SnakeUserData) userData;
    }

    Vector2 velocity4;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


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
            speed = defaultSpeed;
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

        batch.draw(snakeRegion, x, y);
    }

    public void setNewHeading(float targetX, float targetY) {

        touch.set(targetX, targetY);
        angleChange = true;
    }

    public void stopMovement() {
        stopMovement = true;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

}