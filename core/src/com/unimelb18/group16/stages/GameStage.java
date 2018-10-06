package com.unimelb18.group16.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.unimelb18.group16.actors.Background;
import com.unimelb18.group16.actors.Enemy;
import com.unimelb18.group16.actors.Ground;
import com.unimelb18.group16.actors.Runner;
import com.unimelb18.group16.actors.Score;
import com.unimelb18.group16.actors.Snake;
import com.unimelb18.group16.actors.menu.AboutButton;
import com.unimelb18.group16.actors.menu.AboutLabel;
import com.unimelb18.group16.actors.menu.AchievementsButton;
import com.unimelb18.group16.actors.menu.ChangeSkinButton;
import com.unimelb18.group16.actors.menu.GameLabel;
import com.unimelb18.group16.actors.menu.LeaderboardButton;
import com.unimelb18.group16.actors.menu.MusicButton;
import com.unimelb18.group16.actors.menu.PauseButton;
import com.unimelb18.group16.actors.menu.PausedLabel;
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
import com.unimelb18.group16.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener {

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private World world;
    private Ground ground;
    private Runner runner;
    private Snake snake;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private SoundButton soundButton;
    private MusicButton musicButton;
    private PauseButton pauseButton;
    private StartButton startButton;
    private LeaderboardButton leaderboardButton;
    private AboutButton aboutButton;
    private ChangeSkinButton changeSkinButton;
    private ShareButton shareButton;
    private AchievementsButton achievementsButton;

    private Score score;
    private float totalTimePassed;
    private boolean tutorialShown;


    private Vector3 touchPoint;

    public GameStage() {
//        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
//                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        setUpCamera();
        setUpStageBase();
        setUpGameLabel();
        setUpMainMenu();

        setUpTouchControlAreas();
        Gdx.input.setInputProcessor(this);
        AudioUtils.getInstance().init();


        onGameOver();
    }

    private void setUpStageBase() {
        setUpWorld();
        setUpFixedMenu();
    }

    private void setUpGameLabel() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 8,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);
        addActor(new GameLabel(gameLabelBounds));
    }

    private void setUpSnakePlay() {
        Rectangle snakePlayBounds = new Rectangle(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2,
                getCamera().viewportWidth, getCamera().viewportHeight / 4);

        snake = new Snake(snakePlayBounds);

        addActor(snake);

        for (int i = 0; i < 10; i++) {
            addActor(snake.snakeBodies[i]);
        }
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

    /**
     * These menu buttons are only displayed when the game is over
     */
    private void setUpMainMenu() {
        setUpStart();
        setUpLeaderboard();
        setUpAbout();
        setUpChangeSkin();
        setUpShare();
        setUpAchievements();
    }

    private void setUpStart() {
        Rectangle startButtonBounds = new Rectangle(getCamera().viewportWidth * 6 / 16,
                getCamera().viewportHeight / 2, getCamera().viewportWidth / 3,
                getCamera().viewportWidth / 10);
        startButton = new StartButton(startButtonBounds, new GameStartButtonListener());
        addActor(startButton);
    }

    private void setUpLeaderboard() {
        Rectangle leaderboardButtonBounds = new Rectangle(getCamera().viewportWidth * 6 / 16,
                getCamera().viewportHeight / 4, getCamera().viewportWidth / 3,
                getCamera().viewportWidth / 10);
        leaderboardButton = new LeaderboardButton(leaderboardButtonBounds,
                new GameLeaderboardButtonListener());
        addActor(leaderboardButton);
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

    private void setUpShare() {
        Rectangle shareButtonBounds = new Rectangle(getCamera().viewportWidth / 64,
                getCamera().viewportHeight / 2, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        shareButton = new ShareButton(shareButtonBounds, new GameShareButtonListener());
        addActor(shareButton);
    }

    private void setUpAchievements() {
        Rectangle achievementsButtonBounds = new Rectangle(getCamera().viewportWidth * 23 / 25,
                getCamera().viewportHeight / 2, getCamera().viewportHeight / 10,
                getCamera().viewportHeight / 10);
        achievementsButton = new AchievementsButton(achievementsButtonBounds,
                new GameAchievementsButtonListener());
        addActor(achievementsButton);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpBackground();
        //  setUpGround();
    }

    private void setUpBackground() {
        addActor(new Background());
    }

    private void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpCharacters() {

        setUpSnakePlay();

        //setUpRunner();
        setUpPauseLabel();
        // createEnemy();
    }

    private void setUpRunner() {
        if (runner != null) {
            runner.remove();
        }
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
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

    @Override
    public void act(float delta) {
        super.act(delta);

        if (GameManager.getInstance().getGameState() == GameState.PAUSED) return;

        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            totalTimePassed += delta;
            updateDifficulty();
        }

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation

    }

    private void update(Body body) {
//        if (!BodyUtils.bodyInBounds(body)) {
//            if (BodyUtils.bodyIsEnemy(body) && !runner.isHit()) {
//                createEnemy();
//            }
//            world.destroyBody(body);
//        }
    }

    public boolean touchDragged(int x, int y, int pointer) {


        translateScreenToWorldCoordinates(x, y);

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchDragged(x, y, pointer);
        }

     //   runner.setNewHeading(x, y);
        snake.setNewHeading(x, y);

        return super.touchDragged(x, y, pointer);
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

     //   runner.setNewHeading(x, y);
        snake.setNewHeading(x, y);


//        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
//            runner.jump();
//        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
//            runner.dodge();
//        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

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
                        || leaderboardButton.getBounds().contains(x, y)
                        || aboutButton.getBounds().contains(x, y);
                break;
            case RUNNING:
            case PAUSED:
                touched = pauseButton.getBounds().contains(x, y);
                break;
        }

        return touched || soundButton.getBounds().contains(x, y)
                || musicButton.getBounds().contains(x, y);
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
    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b))) {
            if (runner.isHit()) {
                return;
            }
            runner.hit();
            displayAd();
            GameManager.getInstance().submitScore(score.getScore());
            onGameOver();
            GameManager.getInstance().addGamePlayed();
            GameManager.getInstance().addJumpCount(runner.getJumpCount());
        } else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
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

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

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
            setUpPause();
            setUpTutorial();
            onGameResumed();
        }

    }

    private class GameLeaderboardButtonListener
            implements LeaderboardButton.LeaderboardButtonListener {

        @Override
        public void onLeaderboard() {
            GameManager.getInstance().displayLeaderboard();
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

    private class GameChangeSkinButtonListener implements ChangeSkinButton.ChangeSkinButtonListener {

        @Override
        public void onChangeSkin() {
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

    private class GameShareButtonListener implements ShareButton.ShareButtonListener {

        @Override
        public void onShare() {
            GameManager.getInstance().share();
        }

    }

    private class GameAchievementsButtonListener
            implements AchievementsButton.AchievementsButtonListener {

        @Override
        public void onAchievements() {
            GameManager.getInstance().displayAchievements();
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
    }

    private void onGameAbout() {
        GameManager.getInstance().setGameState(GameState.ABOUT);
        clear();
        setUpStageBase();
        setUpGameLabel();
        setUpAboutText();
        setUpAbout();
    }
}