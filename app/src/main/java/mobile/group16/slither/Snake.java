package mobile.group16.slither;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;

import java.util.ArrayList;

public class Snake {
    private final int MIN_SPEED;
    private final int MAX_SPEED;

    private double x;
    private double y;
    private double speed;
    private double angle;
    private double turnSpeed;

    private double targetAngle;

    private boolean boosting;

    private int maxY;
    private int minY;
    private Rect headRect;

    private int height;
    private int width;
    private Matrix matrix; // for drawBitmap()

    private double headRadius;

    private int shrinkTimer = 0;

    private int color;
    private Bitmap bitmap;
    private Context context;
    private GameView g;

    private int nSegments;
    private ArrayList<SnakeSegment> snakeBody;
    private double tailSeperation; // separation ratio, not distance

    public Snake(GameView g, Context context, int color) {
        this.g = g;
        this.color = color;
        this.context = context;
        MIN_SPEED = 1;
        MAX_SPEED = 20;

        x = Constants.SCREEN_X/2;
        y = Constants.SCREEN_Y/2;
        speed = 10;
        angle = 0;
        turnSpeed = 0.07;

        headRadius = 30;

        // UNUSED
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.worm_eyes);
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        matrix = new Matrix();

        maxY = Constants.SCREEN_Y - (int)headRadius;
        minY = 0;

        boosting = false;

        headRect = new Rect((int) x, (int) y, (int)headRadius, (int)headRadius);

        // create snake body
        nSegments = 15;
        tailSeperation = 3;
        snakeBody = new ArrayList<SnakeSegment>();
        for (int i = 0; i< nSegments; i++) {
            snakeBody.add(new SnakeSegment(
                    context,
                    x, y,
                    (int)headRadius
            ));
        }
    }

    public void update() {
        // update snake direction by turning towards target angle
        // todo not quite working - probably mod in signedDifference
//        double difference = signedDifference(angle, targetAngle);
//        if (difference > turnSpeed) {
//            angle -= turnSpeed;
//        } else if (difference < -turnSpeed) {
//            angle += turnSpeed;
//        } else {
//            angle = targetAngle;
//        }
        angle = targetAngle; // todo remove when turn speed implemented correctly

        // no scaling used for FPS yet
        double dx = speed*Math.cos(angle);
        double dy = speed*Math.sin(angle);
        double scaleTail = 1;
        if (boosting) {
            dx*=Constants.BOOST_MULTIPLIER;
            dy*=Constants.BOOST_MULTIPLIER;
            scaleTail = 1.5;
        } else {
            scaleTail = 1;
        }
        x += dx;
        y += dy;

        // update snake body, by moving each segment towards the one in front
        for (int i = 0; i< nSegments; i++) {
            if (i==0) {
                snakeBody.get(i).moveTowards(x, y, tailSeperation/scaleTail);
            } else {
                snakeBody.get(i).moveTowards(
                        snakeBody.get(i-1).getX(),
                        snakeBody.get(i-1).getY(),
                        tailSeperation/scaleTail
                );
            }
        }

        // shrink if boosting and create a food trail
        shrinkTimer++;
        if (boosting && shrinkTimer > 10 && nSegments > 10) {
            shrinkTimer = 0;
            g.food.add(new Food(
                    context,
                    (int) snakeBody.get(nSegments-1).getX(),
                    (int) snakeBody.get(nSegments-1).getY()
            ));
            snakeBody.remove(nSegments-1);
            nSegments--;
        }

        //update collision rect to match new location
        headRect.left = (int)x - (int)headRadius/2;
        headRect.top = (int)y - (int)headRadius/2;
        headRect.right = (int)(x + (int)headRadius/2);
        headRect.bottom = (int)(y + (int)headRadius/2);
    }

    public void grow(int size) {
        for (int i=0; i<size; i++) {
            addSegment();
            headRadius *= Constants.SNAKE_GROWTH;
            for (SnakeSegment seg: snakeBody) {
                seg.fatten(Constants.SNAKE_GROWTH);
            }
        }
    }

    public void addSegment() {
        int i = nSegments-1;
        snakeBody.add(new SnakeSegment(
                context,
                snakeBody.get(i-1).getX(), snakeBody.get(i-1).getY(),
                (int)headRadius
        ));
        nSegments ++;
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i< nSegments; i++) {
            snakeBody.get(i).draw(canvas, paint);
        }
        // snake head
        if (boosting) {
            paint.setColor(Color.WHITE);
        } else {
            paint.setColor(this.color);
        }
        canvas.drawCircle((int)x, (int)y, (int)headRadius, paint);
    }

    public void setNewHeading(double targetX, double targetY) {
        // get vector between current position and target point
        double dx = targetX - x;
        double dy = targetY - y;
        // find angle between that vector and the positive (right) horizontal axis
        // use atan2 so quadrants are handled correctly
        setTargetAngle(Math.atan2(dy, dx));
    }

    public double signedDifference(double a, double b) {
        // difference between two angles in radians
        // a is src, b is target
        double diff = a - b;
        diff = ( (diff + Math.PI) % 2*Math.PI ) - Math.PI;
        return diff;
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
    }

    public Rect getHeadRect() {
        return headRect;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getSpeed() {
        return speed;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

    public void setMatrix(float touchPosition) {
        // UNUSED - required for drawBitmap, not drawCircle
        angle =getX()-touchPosition;
        matrix.setRotate((float) angle);
//        matrix.postTranslate((float) getX()+getWidth(), (float) getY()+getHeight());
    }

    public void translateMatrix(float x, float y){
        matrix.postTranslate(x, y);
    }

    public double getAngle() {
        return angle;
    }
}