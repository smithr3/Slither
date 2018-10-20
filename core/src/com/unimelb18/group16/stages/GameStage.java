package com.unimelb18.group16.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.unimelb18.group16.actors.ChangeSkinSnake;
import com.unimelb18.group16.actors.Enemy;
import com.unimelb18.group16.actors.Food;
import com.unimelb18.group16.actors.Ground;
import com.unimelb18.group16.actors.Runner;
import com.unimelb18.group16.actors.Score;
import com.unimelb18.group16.actors.Snake;
import com.unimelb18.group16.actors.SnakeBody;
import com.unimelb18.group16.actors.menu.AboutButton;
import com.unimelb18.group16.actors.menu.AboutLabel;
import com.unimelb18.group16.actors.menu.ArrowModeButton;
import com.unimelb18.group16.actors.menu.ChangeSkinButton;
import com.unimelb18.group16.actors.menu.ChangeSkinSaveButton;
import com.unimelb18.group16.actors.menu.ClassicModeButton;
import com.unimelb18.group16.actors.menu.GameLabel;
import com.unimelb18.group16.actors.menu.JoyPadModeButton;
import com.unimelb18.group16.actors.menu.LeftChangeSkinButton;
import com.unimelb18.group16.actors.menu.MultiPlayerButton;
import com.unimelb18.group16.actors.menu.MusicButton;
import com.unimelb18.group16.actors.menu.PauseButton;
import com.unimelb18.group16.actors.menu.PausedLabel;
import com.unimelb18.group16.actors.menu.RightChangeSkinButton;
import com.unimelb18.group16.actors.menu.SettingsButton;
import com.unimelb18.group16.actors.menu.SettingsSaveButton;
import com.unimelb18.group16.actors.menu.ShareButton;
import com.unimelb18.group16.actors.menu.SoundButton;
import com.unimelb18.group16.actors.menu.StartButton;
import com.unimelb18.group16.actors.menu.Tutorial;
import com.unimelb18.group16.enums.Difficulty;
import com.unimelb18.group16.enums.GameState;
import com.unimelb18.group16.utils.AudioUtils;
import com.unimelb18.group16.utils.BodyUtils;
import com.unimelb18.group16.utils.Constants;
import com.unimelb18.group16.utils.GameManager;
import com.unimelb18.group16.utils.SharedData;
import com.unimelb18.group16.utils.WorldUtils;

import java.util.ArrayList;
import java.util.Random;

public class GameStage extends Stage implements ContactListener, GestureDetector.GestureListener {

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private World world;
    private Ground ground;
    private Runner runner;
    private Snake snake;

    private ArrayList<Vector2> foodOnDeath;

    ArrayList<Body> destroyBodyList;

    private ChangeSkinSnake changeSkinSnake;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private SoundButton soundButton;
    private MusicButton musicButton;
    private PauseButton pauseButton;
    private StartButton startButton;
    private MultiPlayerButton multiPlayerButton;
    private AboutButton aboutButton;
    private ChangeSkinButton changeSkinButton;
    private ShareButton shareButton;
    private SettingsButton settingsButton;
    private LeftChangeSkinButton leftChangeSkinButton;
    private RightChangeSkinButton rightChangeSkinButton;
    private ChangeSkinSaveButton changeSkinSaveButton;
    private SettingsSaveButton settingsSaveButton;

    private Score score;
    private float totalTimePassed;
    private boolean tutorialShown;

    private ArrayList<Snake> enemySnakes;

    private ShapeRenderer shape;

    private int maxSnakes = 0;

    GestureDetector gestureDetector;

    private Vector3 touchPoint;

    public GameStage() {
//        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
//                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        shape = new ShapeRenderer();

        setUpCamera();
        setUpStageBase();
        setUpGameLabel();
        setUpMainMenu();
        destroyBodyList = new ArrayList<Body>();

        foodOnDeath = new ArrayList<Vector2>();

        enemySnakes = new ArrayList<Snake>();

        setUpTouchControlAreas();
        Gdx.input.setInputProcessor(this);

        gestureDetector = new GestureDetector(this);
        //  Gdx.input.setInputProcessor(gestureDetector);
        //   Gdx.input.setInputProcessor(this);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);

        AudioUtils.getInstance().init();

        //    createGround();
        // createBox();

        onGameOver();


    }

    private void setUpStageBase() {
        setUpWorld();

        WorldUtils.createLeftWorldEnd(world);
        WorldUtils.createRightWorldEnd(world);
        WorldUtils.createTopWorldEnd(world);
        WorldUtils.createBottomWorldEnd(world);

        //  setUpFixedMenu();
    }

    private void setUpGameLabel() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 8,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new GameLabel(gameLabelBounds));
    }


    private void setUpChangeSkinSnake() {
        Rectangle snakeSkinBounds = new Rectangle(getCamera().viewportWidth, getCamera().viewportHeight,
                getCamera().viewportWidth, getCamera().viewportHeight);

        changeSkinSnake = new ChangeSkinSnake(snakeSkinBounds);

        addActor(changeSkinSnake);
    }

    private void setUpAboutText() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 5 / 8,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new AboutLabel(gameLabelBounds));
    }

    /**
     * These menu buttons are always displayed
     */
    private void setUpFixedMenu() {
        setUpSound();
        setUpMusic();
        setUpScore();
    }

    private void setUpSound() {
        Rectangle soundButtonBounds = new Rectangle(getCamera().viewportWidth / 64,
                getCamera().viewportHeight * 13 / 20, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        soundButton = new SoundButton(soundButtonBounds);
        addActor(soundButton);
    }

    private void setUpMusic() {
        Rectangle musicButtonBounds = new Rectangle(getCamera().viewportWidth / 64,
                getCamera().viewportHeight * 4 / 5, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        musicButton = new MusicButton(musicButtonBounds);
        addActor(musicButton);
    }

    private void setUpScore() {
        Rectangle scoreBounds = new Rectangle(getCamera().viewportWidth * 47 / 64,
                getCamera().viewportHeight * 57 / 64, getCamera().viewportWidth / 4,
                getCamera().viewportHeight / 8);
        score = new Score(scoreBounds);
        addActor(score);
    }

    private void setUpPause() {
        Rectangle pauseButtonBounds = new Rectangle(getCamera().viewportWidth / 64,
                getCamera().viewportHeight * 1 / 2, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        pauseButton = new PauseButton(pauseButtonBounds, new GamePauseButtonListener());
        addActor(pauseButton);
    }

    ClassicModeButton classicModeButton;
    ArrowModeButton arrowModeButton;
    JoyPadModeButton joyPadModeButton;

    /**
     * These menu buttons are only displayed when the game is over
     */
    private void setUpMainMenu() {
        setUpStart();
        setUpMultiPlayer();
        setUpAbout();
        setUpChangeSkin();
        setUpShare();
        setUpSettings();


    }

    private void addSettingsButton() {
        Rectangle classModeBound = new Rectangle(getCamera().viewportWidth * 3 / 20,
                getCamera().viewportHeight * 1 / 3, getCamera().viewportWidth / 4,
                getCamera().viewportWidth / 4);


        classicModeButton = new ClassicModeButton(classModeBound, new GameClassicModeButtonListener());

        addActor(classicModeButton);

        Rectangle arrowModeBound = new Rectangle(getCamera().viewportWidth * 7 / 20,
                getCamera().viewportHeight * 1 / 3, getCamera().viewportWidth / 4,
                getCamera().viewportWidth / 4);


        arrowModeButton = new ArrowModeButton(arrowModeBound, new GameArrowModeButtonListener());

        addActor(arrowModeButton);

        Rectangle joyPadModeBound = new Rectangle(getCamera().viewportWidth * 13 / 20,
                getCamera().viewportHeight * 1 / 3, getCamera().viewportWidth / 5,
                getCamera().viewportWidth / 4);


        joyPadModeButton = new JoyPadModeButton(joyPadModeBound, new GameJoyPadModeButtonListener());

        addActor(joyPadModeButton);
    }

    private void setUpStart() {
        Rectangle startButtonBounds = new Rectangle(getCamera().viewportWidth * 6 / 16,
                getCamera().viewportHeight / 2, getCamera().viewportWidth / 3,
                getCamera().viewportWidth / 10);
        startButton = new StartButton(startButtonBounds, new GameStartButtonListener());
        addActor(startButton);
    }

    private void setUpMultiPlayer() {
        Rectangle multiPlayerButtonBounds = new Rectangle(getCamera().viewportWidth * 6 / 16,
                getCamera().viewportHeight / 4, getCamera().viewportWidth / 3,
                getCamera().viewportWidth / 10);
        multiPlayerButton = new MultiPlayerButton(multiPlayerButtonBounds,
                new GameMultiPlayerButtonListener());
        addActor(multiPlayerButton);
    }

    private void setUpAbout() {
        Rectangle aboutButtonBounds = new Rectangle(getCamera().viewportWidth * 23 / 25,
                getCamera().viewportHeight * 13 / 20, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        aboutButton = new AboutButton(aboutButtonBounds, new GameAboutButtonListener());
        addActor(aboutButton);
    }

    private void setUpChangeSkin() {
        Rectangle changeSkinButtonBounds = new Rectangle(getCamera().viewportWidth / 64,
                getCamera().viewportHeight * 6 / 20, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        changeSkinButton = new ChangeSkinButton(changeSkinButtonBounds, new GameChangeSkinButtonListener());
        addActor(changeSkinButton);
    }

    private void setUpLeftChangeSkinButton() {
        Rectangle leftChangeSkinButtonBounds = new Rectangle(getCamera().viewportWidth * 1 / 20,
                getCamera().viewportHeight / 2 - (getCamera().viewportHeight / 8 / 2), getCamera().viewportWidth / 6,
                getCamera().viewportHeight / 8);
        leftChangeSkinButton = new LeftChangeSkinButton(leftChangeSkinButtonBounds, new LeftChangeSkinButtonListener());
        addActor(leftChangeSkinButton);
    }

    private void setUpRightChangeSkinButton() {
        Rectangle rightChangeSkinButtonBounds = new Rectangle(getCamera().viewportWidth * 4 / 5,
                getCamera().viewportHeight / 2 - (getCamera().viewportHeight / 8 / 2), getCamera().viewportWidth / 6,
                getCamera().viewportHeight / 8);
        rightChangeSkinButton = new RightChangeSkinButton(rightChangeSkinButtonBounds, new RightChangeSkinButtonListener());
        addActor(rightChangeSkinButton);
    }

    private void setChangeSkinSaveButton() {
        Rectangle changeSkinSaveButtonBounds = new Rectangle(getCamera().viewportWidth / 2,
                getCamera().viewportHeight * 1 / 5, getCamera().viewportHeight / 5,
                getCamera().viewportHeight / 10);
        changeSkinSaveButton = new ChangeSkinSaveButton(changeSkinSaveButtonBounds, new ChangeSkinSaveButtonListener());
        addActor(changeSkinSaveButton);
    }

    private void setSettingsSaveButton() {
        Rectangle changeSkinSaveButtonBounds = new Rectangle(getCamera().viewportWidth / 2,
                getCamera().viewportHeight * 1 / 5, getCamera().viewportHeight / 5,
                getCamera().viewportHeight / 10);
        settingsSaveButton = new SettingsSaveButton(changeSkinSaveButtonBounds, new GameSettingsSaveButtonListener());
        addActor(settingsSaveButton);
    }

    private void setUpShare() {
        Rectangle shareButtonBounds = new Rectangle(getCamera().viewportWidth / 64,
                getCamera().viewportHeight / 2, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        shareButton = new ShareButton(shareButtonBounds, new GameShareButtonListener());
        addActor(shareButton);
    }

    private void setUpSettings() {
        Rectangle settingsButtonBounds = new Rectangle(getCamera().viewportWidth * 23 / 25,
                getCamera().viewportHeight / 2, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        settingsButton = new SettingsButton(settingsButtonBounds,
                new GameSettingsButtonListener());
        addActor(settingsButton);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);

        setUpBackground();
        // setUpGround();

    }

    private void setUpBackground() {
        // addActor(new Background(getCamera()));
    }

    private void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpCharacters() {

        setUpSnakePlay();

        //  setUpRunner();
        setUpPauseLabel();
        //   createEnemy();

        Random random = new Random();

        createFood(random.nextInt((int) getCamera().viewportWidth), random.nextInt((int) getCamera().viewportHeight));
    }

    private void setUpRunner() {
        if (runner != null) {
            runner.remove();
        }
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setUpSnakePlay() {
        if (snake != null) {
            snake.remove();
        }
        snake = new Snake(WorldUtils.createSnakeHead(world, getCamera().position.x, getCamera().position.y));
        snake.setCamera(getCamera());
        addActor(snake);

    }


    private void createEnemy() {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        enemy.getUserData().setLinearVelocity(
                GameManager.getInstance().getDifficulty().getEnemyLinearVelocity());
        addActor(enemy);
    }

    private void createFood(float x, float y) {
        Food food = new Food(WorldUtils.createFood(world, x, y));
        food.setCamera(getCamera());
        addActor(food);
    }

    private void addEnemySnake() {
        Random random = new Random();
        Snake snake = new Snake(WorldUtils.createSnakeHead(world, random.nextInt(Constants.APP_WIDTH - 500), random.nextInt(Constants.APP_HEIGHT - 500)));
        enemySnakes.add(snake);
        addActor(snake);
    }

    private void setUpCameraViewPort() {
        camera.setToOrtho(false, Constants.CAMERA_DEFAULT_WIDTH, Constants.CAMERA_DEFAULT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(Constants.CAMERA_DEFAULT_WIDTH, Constants.CAMERA_DEFAULT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);

        camera.update();

        getViewport().setCamera(camera);
    }

    private void setUpTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0,
                getCamera().viewportWidth / 2, getCamera().viewportHeight);
    }

    private void setUpPauseLabel() {
        Rectangle pauseLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 8,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new PausedLabel(pauseLabelBounds));
    }

    private void setUpTutorial() {
        if (tutorialShown) {
            return;
        }
        setUpLeftTutorial();
        setUpRightTutorial();
        tutorialShown = true;
    }

    private void setUpLeftTutorial() {
        float width = getCamera().viewportHeight / 4;
        float x = getCamera().viewportWidth / 4 - width / 2;
        Rectangle leftTutorialBounds = new Rectangle(x, getCamera().viewportHeight * 9 / 20, width,
                width);
        addActor(new Tutorial(leftTutorialBounds, Constants.TUTORIAL_LEFT_REGION_NAME,
                Constants.TUTORIAL_LEFT_TEXT));
    }


    private void setUpRightTutorial() {
        float width = getCamera().viewportHeight / 4;
        float x = getCamera().viewportWidth * 3 / 4 - width / 2;
        Rectangle rightTutorialBounds = new Rectangle(x, getCamera().viewportHeight * 9 / 20, width,
                width);
        addActor(new Tutorial(rightTutorialBounds, Constants.TUTORIAL_RIGHT_REGION_NAME,
                Constants.TUTORIAL_RIGHT_TEXT));
    }

    float timer = 0;

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    float newPortWidth = Constants.CAMERA_DEFAULT_WIDTH;
    float newPortHeight = Constants.CAMERA_DEFAULT_HEIGHT;

    @Override
    public void act(float delta) {
        super.act(delta);

        if (GameManager.getInstance().getGameState() == GameState.PAUSED) return;

        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            totalTimePassed += delta;
            //  updateDifficulty();
        }

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }


        destroyBody();

        createFoodOnDeath();

        timer += 1 * delta;


        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {


            Random random = new Random();

            if (timer >= 1f) {

                for (Snake snake : enemySnakes) {
                    snake.setNewHeading(random.nextInt(Constants.APP_WIDTH - 200), random.nextInt(Constants.APP_HEIGHT - 200));
                }

                createFood(random.nextInt(Constants.APP_WIDTH), random.nextInt(Constants.APP_HEIGHT));

                if (maxSnakes < 5) {
                    addEnemySnake();
                    maxSnakes++;
                }
                timer -= 1f;
            }
        }
        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            if (snake != null) {

                int snakeBodies = snake.snakeBodies.size();

                if (newPortWidth < Constants.APP_WIDTH) {
                    newPortWidth = Constants.CAMERA_DEFAULT_WIDTH + snakeBodies * 4f;
                }

                if (newPortHeight < Constants.APP_HEIGHT) {
                    newPortHeight = Constants.CAMERA_DEFAULT_HEIGHT + snakeBodies * 4f;
                }
                //  camera.zoom = (float) (snakeBodies * 0.1);
                camera.setToOrtho(false, newPortWidth, newPortHeight);

                updateCamera();

            }
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        shape.setProjectionMatrix(camera.combined);

        shape.setColor(Color.YELLOW);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rectLine(0, 0, Constants.APP_WIDTH, 0, 20);
        shape.end();

        shape.setColor(Color.TEAL);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rectLine(0, 0, 0, Constants.APP_HEIGHT, 20);
        shape.end();

        shape.setColor(Color.RED);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rectLine(0, Constants.APP_HEIGHT, Constants.APP_WIDTH, Constants.APP_HEIGHT, 20);
        shape.end();

        shape.setColor(Color.BLUE);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rectLine(Constants.APP_WIDTH, Constants.APP_HEIGHT, Constants.APP_WIDTH, 0, 20);
        shape.end();

        //TODO: Implement interpolation

    }

    public void updateCamera() {
        float x = snake.getX();
        float y = snake.getY();
        float halfViewPortWidth = getCamera().viewportWidth / 2;
        float halfViewPortHeight = getCamera().viewportHeight / 2;

        if (x < halfViewPortWidth) {
            getCamera().position.x = halfViewPortWidth;
        } else if (x > Constants.APP_WIDTH - halfViewPortWidth) {
            getCamera().position.x = Constants.APP_WIDTH - halfViewPortWidth;
        } else {
            getCamera().position.x = x;
        }

        if (y < halfViewPortHeight) {
            getCamera().position.y = halfViewPortHeight;
        } else if (y > Constants.APP_HEIGHT - halfViewPortHeight) {
            getCamera().position.y = Constants.APP_HEIGHT - halfViewPortHeight;
        } else {
            getCamera().position.y = y;
        }
        getCamera().update();
    }

    public void moveCamera(float x, float y) {
        if ((snake.getX() > Constants.APP_WIDTH / 2)) {
            camera.position.set(x, y, 0);
            camera.update();
        }

    }

    private void createFoodOnDeath() {
        for (Vector2 food : foodOnDeath) {
            createFood(food.x, food.y);
        }

        foodOnDeath.clear();
    }

    private void destroyBody() {
        for (Body entity : destroyBodyList) {
            world.destroyBody(entity);
        }
        destroyBodyList.clear();
    }

    private void update(Body body) {

    }


    public boolean touchDragged(int x, int y, int pointer) {


        translateScreenToWorldCoordinates(x, y);

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchDragged(x, y, pointer);
        }

        //   runner.setNewHeading(x, y);
        snake.setNewHeading(touchPoint.x, touchPoint.y);

        return super.touchDragged(x, y, pointer);
    }


    public void update(float deltaTime) {

    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);

        // If a menu control was touched ignore the rest
        if (menuControlTouched(touchPoint.x, touchPoint.y)) {
            return super.touchDown(x, y, pointer, button);
        }

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchDown(x, y, pointer, button);
        }


        snake.setNewHeading(touchPoint.x, touchPoint.y);


//        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
//            runner.jump();
//        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
//            runner.dodge();
//        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            if (snake != null) {
                snake.setBoosting(false);
            }

        }


        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchUp(screenX, screenY, pointer, button);
        }

        // runner.setNewHeading(screenX, screenY);


//        if (runner.isDodging()) {
//            runner.stopDodge();
//        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    private boolean menuControlTouched(float x, float y) {
        boolean touched = false;

        switch (GameManager.getInstance().getGameState()) {
            case OVER:
                touched = startButton.getBounds().contains(x, y)
                        || multiPlayerButton.getBounds().contains(x, y)
                        || aboutButton.getBounds().contains(x, y);
                break;
            case RUNNING:
            case PAUSED:
//                touched = pauseButton.getBounds().contains(x, y);
                break;
        }

//        return touched || soundButton.getBounds().contains(x, y)
//                || musicButton.getBounds().contains(x, y);

        return false;
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }

    /**
     * Helper function to get the actual coordinates in my world
     *
     * @param x
     * @param y
     */
    private void translateScreenToWorldCoordinates(float x, float y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }


    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsSnakeHead(a) && BodyUtils.bodyIsWorldEnd(b)) ||
                (BodyUtils.bodyIsWorldEnd(a) && BodyUtils.bodyIsSnakeHead(b))) {

            if (BodyUtils.bodyIsSnakeHead(a)) {
                if (a.getUserData().hashCode() == snake.getUserData().hashCode()) {
                    for (SnakeBody snakeBody : snake.snakeBodies) {
                        foodOnDeath.add(new Vector2(snakeBody.getX(), snakeBody.getY()));
                    }

                    destroyBodyList.add(a);
                    onGameOver();
                    displayAd();
                }
            }

            if (BodyUtils.bodyIsSnakeHead(b)) {
                if (b.getUserData().hashCode() == snake.getUserData().hashCode()) {
                    for (SnakeBody snakeBody : snake.snakeBodies) {
                        foodOnDeath.add(new Vector2(snakeBody.getX(), snakeBody.getY()));
                    }

                    destroyBodyList.add(a);
                    onGameOver();
                    displayAd();
                }
            }

            for (Snake enemySnake : enemySnakes) {
                if (BodyUtils.bodyIsSnakeHead(a)) {
                    if (a.getUserData().hashCode() == enemySnake.getUserData().hashCode()) {
                        destroyBodyList.add(a);
                    }
                }
                if (BodyUtils.bodyIsSnakeHead(b)) {
                    if (b.getUserData().hashCode() == enemySnake.getUserData().hashCode()) {
                        destroyBodyList.add(b);
                    }
                }
            }
        }

        if ((BodyUtils.bodyIsSnakeHead(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsSnakeHead(b))) {


            for (Snake enemySnake : enemySnakes) {
                if (BodyUtils.bodyIsSnakeHead(a)) {
                    if (a.getUserData().hashCode() == enemySnake.getUserData().hashCode()) {
                        enemySnake.addSnakeBody();
                    }
                }
                if (BodyUtils.bodyIsSnakeHead(b)) {
                    if (b.getUserData().hashCode() == enemySnake.getUserData().hashCode()) {
                        enemySnake.addSnakeBody();
                    }
                }
            }
            if (BodyUtils.bodyIsSnakeHead(a)) {
                if (a.getUserData().hashCode() == snake.getUserData().hashCode()) {
                    snake.addSnakeBody();
                }
            }

            if (BodyUtils.bodyIsSnakeHead(b)) {
                if (b.getUserData().hashCode() == snake.getUserData().hashCode()) {
                    snake.addSnakeBody();
                }
            }

            if (BodyUtils.bodyIsEnemy(b)) {
                destroyBodyList.add(b);
            } else if (BodyUtils.bodyIsEnemy(a)) {
                destroyBodyList.add(a);
            }


            // displayAd();
            // GameManager.getInstance().submitScore(score.getScore());
            // onGameOver();
            // GameManager.getInstance().addGamePlayed();
            // GameManager.getInstance().addJumpCount(runner.getJumpCount());
        }
    }


    private void updateDifficulty() {

        if (GameManager.getInstance().isMaxDifficulty()) {
            return;
        }

        Difficulty currentDifficulty = GameManager.getInstance().getDifficulty();

        if (totalTimePassed > GameManager.getInstance().getDifficulty().getLevel() * 5) {

            int nextDifficulty = currentDifficulty.getLevel() + 1;
            String difficultyName = "DIFFICULTY_" + nextDifficulty;
            GameManager.getInstance().setDifficulty(Difficulty.valueOf(difficultyName));

            // runner.onDifficultyChange(GameManager.getInstance().getDifficulty());
            score.setMultiplier(GameManager.getInstance().getDifficulty().getScoreMultiplier());

            displayAd();
        }

    }

    private void displayAd() {
        GameManager.getInstance().displayAd();
    }

    @Override
    public void endContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            if (snake != null && count == 1) {
                snake.setBoosting(true);
            }

        }

        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private class GamePauseButtonListener implements PauseButton.PauseButtonListener {

        @Override
        public void onPause() {
            onGamePaused();
        }

        @Override
        public void onResume() {
            onGameResumed();
        }

    }

    private class GameStartButtonListener implements StartButton.StartButtonListener {

        @Override
        public void onStart() {
            clear();
            setUpStageBase();
            setUpCharacters();
            //  setUpPause();
            //   setUpTutorial();
            onGameResumed();
        }

    }

    private class GameMultiPlayerButtonListener
            implements MultiPlayerButton.MultiPlayerButtonListener {

        @Override
        public void onMultiPlayer() {
            clear();
            setUpStageBase();
            setUpCharacters();
            //   setUpPause();
            //   setUpTutorial();
            onGameResumed();
        }

    }

    private class GameAboutButtonListener implements AboutButton.AboutButtonListener {

        @Override
        public void onAbout() {
            if (GameManager.getInstance().getGameState() == GameState.OVER) {
                onGameAbout();
            } else {
                clear();
                setUpStageBase();
                setUpGameLabel();
                onGameOver();
            }
        }

    }


    private class LeftChangeSkinButtonListener implements LeftChangeSkinButton.LeftChangeSkinButtonButtonListener {

        @Override
        public void onLeftChangeSkin() {
            if (GameManager.getInstance().getGameState() == GameState.CHANGE_SKIN) {
                changeSkinSnake.changeColor(1);
            }
        }

    }


    private class RightChangeSkinButtonListener implements RightChangeSkinButton.RightChangeSkinButtonButtonListener {

        @Override
        public void onRightChangeSkin() {
            if (GameManager.getInstance().getGameState() == GameState.CHANGE_SKIN) {
                changeSkinSnake.changeColor(2);
            }
        }

    }


    private class ChangeSkinSaveButtonListener implements ChangeSkinSaveButton.ChangeSkinSaveButtonListener {

        @Override
        public void onChangeSkinSave() {
            if (GameManager.getInstance().getGameState() == GameState.CHANGE_SKIN) {
                SharedData.setValue("currentSkin", Integer.toString(changeSkinSnake.getCurrentColor()));

                clear();
                setUpStageBase();
                setUpGameLabel();
                onGameOver();
            }
        }

    }

    String selectedMode;

    private class GameSettingsSaveButtonListener implements SettingsSaveButton.SettingsSaveButtonListener {

        @Override
        public void onSettingsSave() {
            if (GameManager.getInstance().getGameState() == GameState.SETTINGS) {

                selectedMode = joyPadModeButton.isChecked() ? "JoyPad" : arrowModeButton.isChecked() ? "Arrow" : classicModeButton.isChecked() ? "Classic" : "";

                SharedData.setValue("setting", selectedMode);
                clear();
                setUpStageBase();
                setUpGameLabel();
                onGameOver();
            }
        }

    }


    private class GameChangeSkinButtonListener implements ChangeSkinButton.ChangeSkinButtonListener {

        @Override
        public void onChangeSkin() {
            if (GameManager.getInstance().getGameState() == GameState.OVER) {
                onGameChangeSkin();
            } else {
                clear();
                setUpStageBase();
                setUpGameLabel();
                onGameOver();
            }
        }

    }

    private class GameClassicModeButtonListener implements ClassicModeButton.ClassicModeButtonListener {

        @Override
        public void onClassicMode() {
            if (GameManager.getInstance().getGameState() == GameState.SETTINGS) {
                classicModeButton.setChecked(true);
                arrowModeButton.setChecked(false);
                joyPadModeButton.select(false);


            }
        }
    }

    private class GameArrowModeButtonListener implements ArrowModeButton.ArrowModeButtonListener {

        @Override
        public void onArrowMode() {
            if (GameManager.getInstance().getGameState() == GameState.SETTINGS) {
                classicModeButton.select(false);
                arrowModeButton.select(true);
                joyPadModeButton.select(false);
            }
        }
    }

    private class GameJoyPadModeButtonListener implements JoyPadModeButton.JoyPadModeButtonListener {

        @Override
        public void onJoyPadMode() {
            if (GameManager.getInstance().getGameState() == GameState.SETTINGS) {
                classicModeButton.select(false);
                arrowModeButton.select(false);
                joyPadModeButton.select(true);
            }
        }
    }

    private class GameShareButtonListener implements ShareButton.ShareButtonListener {

        @Override
        public void onShare() {
            GameManager.getInstance().share();
        }

    }

    private class GameSettingsButtonListener
            implements SettingsButton.SettingsButtonListener {

        @Override
        public void onSettings() {
            onGameSettings();
        }

    }

    private void onGamePaused() {
        GameManager.getInstance().setGameState(GameState.PAUSED);
    }

    private void onGameResumed() {
        GameManager.getInstance().setGameState(GameState.RUNNING);
    }

    private void onGameOver() {
        GameManager.getInstance().setGameState(GameState.OVER);
        GameManager.getInstance().resetDifficulty();
        totalTimePassed = 0;
        setUpMainMenu();

        setUpCameraViewPort();
    }

    private void onGameAbout() {
        GameManager.getInstance().setGameState(GameState.ABOUT);
        clear();
        setUpStageBase();
        setUpGameLabel();
        setUpAboutText();
        setUpAbout();
    }

    private void onGameSettings() {
        GameManager.getInstance().setGameState(GameState.SETTINGS);
        clear();
        setUpStageBase();
        setUpGameLabel();

        addSettingsButton();
        setSettingsSaveButton();

        selectedMode = SharedData.getKey("setting");

        if (selectedMode.equals("JoyPad")) {
            joyPadModeButton.select(true);
        } else if (selectedMode.equals("Classic")) {
            classicModeButton.select(true);
        } else if (selectedMode.equals("Arrow")) {
            arrowModeButton.select(true);
        }
    }

    private void onGameChangeSkin() {
        GameManager.getInstance().setGameState(GameState.CHANGE_SKIN);
        clear();
        setUpStageBase();
        setUpGameLabel();

        setUpChangeSkinSnake();

        setUpLeftChangeSkinButton();
        setUpRightChangeSkinButton();
        setChangeSkinSaveButton();
    }
}