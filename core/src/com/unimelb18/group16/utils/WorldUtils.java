package com.unimelb18.group16.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.unimelb18.group16.box2d.EnemyUserData;
import com.unimelb18.group16.box2d.GroundUserData;
import com.unimelb18.group16.box2d.PowerFoodData;
import com.unimelb18.group16.box2d.RunnerUserData;
import com.unimelb18.group16.box2d.SnakeUserData;
import com.unimelb18.group16.box2d.WorldEndUserData;
import com.unimelb18.group16.enums.EnemyType;

public class WorldUtils {

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createGround(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.GROUND_X, Constants.GROUND_Y));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2, Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setUserData(new GroundUserData(Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createEnemy(World world) {
        EnemyType enemyType = RandomUtils.getRandomEnemyType();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(enemyType.getX(), enemyType.getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(enemyType.getWidth() / 2, enemyType.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, enemyType.getDensity());
        body.resetMassData();
        EnemyUserData userData = new EnemyUserData(enemyType.getWidth(), enemyType.getHeight(),
                enemyType.getAnimationAssetId());
        body.setUserData(userData);


        shape.dispose();
        return body;


    }

    public static Body createFood(World world, float x, float y) {
        EnemyType enemyType = RandomUtils.getRandomEnemyType();

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(10);
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(x, y);
        Body groundBody = world.createBody(groundBodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        groundBody.createFixture(fixtureDef);
        groundBody.resetMassData();
        EnemyUserData userData = new EnemyUserData(enemyType.getWidth(), enemyType.getHeight(),
                enemyType.getAnimationAssetId());
        groundBody.setUserData(userData);
        circleShape.dispose();

        return groundBody;
    }

    public static Body createPowerFood(World world, float x, float y, int foodType) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(30);
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(x, y);
        Body groundBody = world.createBody(groundBodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        groundBody.createFixture(fixtureDef);
        groundBody.resetMassData();
        PowerFoodData userData = new PowerFoodData(64, 63,
                foodType);
        groundBody.setUserData(userData);
        circleShape.dispose();

        return groundBody;
    }

    public static Body createSnakeHead(World world, float x, float y) {
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(50, 50);
        BodyDef boxBodyDef = new BodyDef();

        boxBodyDef.position.set(x, y);
        boxBodyDef.angle = MathUtils.PI / 32;
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        boxBodyDef.fixedRotation = false;
        Body boxBody = world.createBody(boxBodyDef);
        FixtureDef boxFixtureDef = new FixtureDef();
        boxFixtureDef.shape = boxShape;
        boxFixtureDef.restitution = 0.75f;
        boxFixtureDef.density = 2.0f;
        boxBody.createFixture(boxFixtureDef);
        boxBody.resetMassData();
        boxBody.setUserData(new SnakeUserData(50, 50));
        boxShape.dispose();

        return boxBody;
    }

    public static Body createLeftWorldEnd(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(0, Constants.APP_HEIGHT));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(-40, Constants.APP_HEIGHT);
        body.createFixture(shape, 1000);
        body.setUserData(new WorldEndUserData(1, Constants.APP_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createRightWorldEnd(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.APP_WIDTH, Constants.APP_HEIGHT));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20, Constants.APP_HEIGHT);
        body.createFixture(shape, 1000);
        body.setUserData(new WorldEndUserData(20, Constants.APP_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createTopWorldEnd(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(0, Constants.APP_HEIGHT - 40));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.APP_WIDTH, -10);
        body.createFixture(shape, 1000);
        body.setUserData(new WorldEndUserData(Constants.APP_WIDTH, 20));
        shape.dispose();
        return body;
    }

    public static Body createBottomWorldEnd(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(20, 0));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.APP_WIDTH - 20, -40);
        body.createFixture(shape, 1000);
        body.setUserData(new WorldEndUserData(Constants.APP_WIDTH, 20));
        shape.dispose();
        return body;
    }

}
