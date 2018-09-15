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

    public Player(Context context, int screenX, int screenY) {
        snake = new Snake(context, screenX, screenY);
    }

    public void update() {
         snake.update();
    }

    public void draw(Canvas canvas, Paint paint) {
        snake.draw(canvas, paint);
    }

    public void handleTouchInput(MotionEvent motionEvent) {
        snake.setNewHeading(
                motionEvent.getX(),
                motionEvent.getY()
        );
    }
}