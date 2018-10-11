package mobile.group16.slither;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Food {

    private int x;
    private int y;

    private int size;

    private boolean isEaten = false;

    public Food(Context context, int size) {
        this.size = size;
        x = randomInt(0, Constants.SCREEN_X);
        y = randomInt(0, Constants.SCREEN_Y);
    }

    public Food(Context context, int x, int y) {
        this.size = 1;
        this.x = x;
        this.y = y;
    }

    public void update() {
        // todo make largest food move around slowly
    }

    public void eat() {
        isEaten = true;
    }

    public boolean notEaten() {
        return !isEaten;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, size*4+3, paint);
    }

    private int randomInt(int min, int max) {
        // todo move this code somewhere global, SimpleAI also uses it
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}