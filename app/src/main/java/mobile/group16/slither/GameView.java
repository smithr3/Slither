package mobile.group16.slither;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;

    private Thread gameThread = null;

    private Player player;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    Paint trailPaint; // todo remove probably

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

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
        player = new Player(context, screenX, screenY);

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
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            //Drawing the player
            // todo refactor paint into objects that actually use it
            player.draw(canvas, paint);

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