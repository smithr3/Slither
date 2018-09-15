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

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private Player player;
    private SnakeTrail snakeTrail;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Adding enemies object array
    private Enemy[] enemies;

    //Adding 3 enemies you may increase the size
    private int enemyCount = 3;

    private int snakeTrailCount = 1;

    //Adding an stars list
    private ArrayList<Star> stars = new
            ArrayList<Star>();

    //defining a boom object to display blast
    private Boom boom;

    Paint trailPaint;

    //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        trailPaint = new Paint();
        trailPaint.setColor(Color.BLUE);
        trailPaint.setStrokeWidth(1);
        trailPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        trailPaint.setShader(new RadialGradient(30, 40,
                40, Color.TRANSPARENT, Color.RED, Shader.TileMode.MIRROR));




        //initializing player object
        //this time also passing screen size to player constructor
        player = new Player(context, screenX, screenY);
        snakeTrail = new SnakeTrail(context, screenX, screenY, 100, 100);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 100 stars you may increase the number
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }

        //initializing enemy object array
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        //initializing boom object
        boom = new Boom(context);
    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    private void update() {
        player.update();

        //setting boom outside the screen
        boom.setX(-250);
        boom.setY(-250);

        for (Star s : stars) {
            s.update(player.getSpeed());
        }

        //updating the enemy coordinate with respect to player speed
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update(player.getSpeed());

            //if collision occurrs with player
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {
                //moving enemy outside the left edge

                //displaying boom at that location
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());

                enemies[i].setX(-200);

                snakeTrailCount++;

                player.setX(player.getX() + (player.getWidth() / 4));
            }
        }
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.BLACK);

            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);

            //drawing all stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

//            canvas.translate(player.getX()+(player.getWidth()/4), player.getY()+(player.getHeight()/2));
//
////            snakeTrail.setX(player.getX()+(player.getWidth()/4));
////            snakeTrail.setY(player.getY()+(player.getHeight()/2));
//
//            snakeTrail.getDrawable().draw(canvas);
//
//
//
//            canvas.save();
//
//            canvas.restore();


            // canvas.drawCircle(player.getX()+(player.getWidth()/4), player.getY()+(player.getHeight()/2), 10, mPaint);
            for (int i = 0; i < snakeTrailCount; i++) {
                canvas.drawCircle(player.getX() + (player.getWidth() / 4) - (i * player.getWidth() / 4), player.getY() + (player.getHeight() / 2), 30, trailPaint);

            }

            player.translateMatrix((float)(Math.cos(player.getRotateDegree())),
                    (float)(Math.sin(player.getRotateDegree())));

            player.setX((int)Math.cos(player.getRotateDegree()));
            player.setY((int)Math.sin(player.getRotateDegree()));

            //Drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getMatrix(),
                    paint);

            //drawing the enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }


            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

//    Point CalculateBezierPoint(float t, Point s, Point c1, Point c2, Point e)
//    {
//        float u = 1 - t;
//        float tt = t*t;
//        float uu = u*u;
//        float uuu = uu * u;
//        float ttt = tt * t;
//
//        Point p = new Point(s.x * uuu, s.y * uuu);
//        p.x += 3 * uu * t * c1.x;
//        p.y += 3 * uu * t * c1.y;
//        p.x += 3 * u * tt * c2.x;
//        p.y += 3 * u * tt * c2.y;
//        p.x += ttt * e.x;
//        p.y += ttt * e.y;
//
//        return p;
//    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user presses on the screen
                //we will do something here
                //player.stopBoosting();

                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                //do something here
                //player.setBoosting();
                player.handleTouchInput(motionEvent);
                break;
        }
        return true;
    }
}