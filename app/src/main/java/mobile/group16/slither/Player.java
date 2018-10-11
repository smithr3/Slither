package mobile.group16.slither;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.view.MotionEvent;

public class Player {
    private Snake snake;

    private int doubleTapTimer;
    private GameView g;

    public Player(GameView g, Context context) {
        this.g = g;
        snake = new Snake(g, context, Color.GREEN);
    }

    public void update() {
        doubleTapTimer++;
        snake.update();
    }

    public void draw(Canvas canvas, Paint paint) {
        snake.draw(canvas, paint);
    }

    public void handleTouchInput(float x, float y) {
        // x/y in game world coords
        snake.setNewHeading(x, y);

        if (doubleTapTimer > 60) {
            // todo actual double tap, not just holding down mouse for 300 consective ticks
            startBoosting();
            doubleTapTimer = 0;
        }
    }

    public void startBoosting() {
        snake.setBoosting();
    }

    public void stopBoosting() {
        snake.stopBoosting();
        doubleTapTimer = 0;
    }

    public Snake getSnake() {
        return snake;
    }
}