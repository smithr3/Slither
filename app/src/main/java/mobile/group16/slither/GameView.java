package mobile.group16.slither;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;

    private Thread gameThread = null;

    private Player player;
    private SimpleAI ai;
    private ArrayList<Food> food;
    private ArrayList<Food> eatenFood; // each update this list is emptied and obj removed from food

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    Paint trailPaint; // todo remove probably

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        // save screen size
        Constants.SCREEN_X = screenX;
        Constants.SCREEN_Y = screenY;

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        trailPaint = new Paint();
        trailPaint.setColor(Color.BLUE);
        trailPaint.setStrokeWidth(1);
        trailPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        trailPaint.setShader(new RadialGradient(
                30, 40,
                40,
                Color.TRANSPARENT,
                Color.RED,
                Shader.TileMode.MIRROR
        ));

        // initialize game objects
        // todo move context and screen into Constants/Globals?
        player = new Player(context);
        ai = new SimpleAI(context);
        food = new ArrayList<Food>();
        eatenFood = new ArrayList<Food>();
        for (int i=0; i<Constants.FOOD; i++) {
            food.add(new Food(context, randomInt(1, 3)));
        }
    }

    private int randomInt(int min, int max) {
        // todo move this code somewhere global, SimpleAI also uses it
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        player.update();
        ai.update();

        // collisions between food and snakes
        // todo clean up or fatten rects, mustn't be positioned nicely as hard to eat food
        for (Food f: food) {
            if (player.snake.getHeadRect().contains(f.getX(), f.getY())
                    && f.notEaten()) {
                player.snake.grow(f.getSize());
                f.eat();
                eatenFood.add(f);
            }
        }

        // purge eaten food
        for (Food f: eatenFood) {
            food.remove(f);
            // todo create new food to replace, probably just move food
        }
        eatenFood.clear();
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);

            // todo refactor paint into objects that actually use it
            player.draw(canvas, paint);
            ai.draw(canvas, paint);
            for (Food f: food) {
                f.draw(canvas, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException ignored) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                // finger off screen
                //player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                // finger touch screen
                //player.setBoosting();
                player.handleTouchInput(motionEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                // moving finger on screen
                player.handleTouchInput(motionEvent);
                break;
        }
        return true;
    }
}