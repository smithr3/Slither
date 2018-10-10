package mobile.group16.slither;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleAI {
    private Snake snake;
    private int count;
    private int screenX;
    private int screenY;

    public SimpleAI(Context context) {
        snake = new Snake(context);
    }

    public void update() {
        count ++;

        // every 50 to 600 ticks move to random point on screen
        if ( count > randomInt(50, 600)) {
            snake.setNewHeading(
                    randomInt(0, Constants.SCREEN_X),
                    randomInt(0, Constants.SCREEN_Y)
            );
            count = 0;
        }

        snake.update();
    }

    public void draw(Canvas canvas, Paint paint) {
        snake.draw(canvas, paint);
    }

    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

}