package mobile.group16.slither;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Player {
    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = -10;
    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    //Bitmap to get character from image
    private Bitmap bitmap;
    //coordinates
    private int x;
    private int y;
    //motion speed of the character
    private int speed = 0;
    //boolean variable to track the ship is boosting or not
    private boolean boosting;
    //Controlling Y coordinate so that ship won't go outside the screen
    private int maxY;
    private int minY;
    private Rect detectCollision;

    private int height;
    private int width;
    private Matrix matrix;

    private float rotateDegree;

    //constructor
    public Player(Context context, int screenX, int screenY) {
        x = screenX/2;
        y = screenY/2;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.worm_eyes);

        matrix = new Matrix();

        height = bitmap.getHeight();
        width = bitmap.getWidth();

        rotateDegree=0;

        //calculating maxY
        maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero
        minY = 0;

        //setting the boosting value to false initially
        boosting = false;

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    //setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    //setting boosting false
    public void stopBoosting() {
        boosting = false;
    }

    /*
     * These are getters you can generate it automatically
     * right click on editor -> generate -> getters
     * */

    public void update() {
        //if the ship is boosting
        if (boosting) {
            //speeding up the ship
            speed += 2;
        } else {
            //slowing down if not boosting
            speed -= 5;
        }
        //controlling the top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        //moving the ship down
        // y -= speed + GRAVITY;

        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        //adding top, left, bottom and right to the rect object
//        detectCollision.left = x;
//        detectCollision.top = y;
//        detectCollision.right = x + bitmap.getWidth();
//        detectCollision.bottom = y + bitmap.getHeight();
    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSpeed() {
        return speed;
    }

    public void handleTouchInput(MotionEvent motionEvent) {

        this.setX((int) motionEvent.getX());
        this.setY((int) motionEvent.getY());

//        setMatrix(motionEvent.getX());

    }

    public void setMatrix(float touchPosition) {

//        rotateDegree=getX()-touchPosition;
//        matrix.setRotate(rotateDegree);
        ///matrix.postTranslate((float) getX()+getWidth(), (float) getY()+getHeight());
    }

    public void translateMatrix(float x, float y){
        matrix.postTranslate(x, y);
    }

    public float getRotateDegree() {
        return rotateDegree;
    }

    public Matrix getMatrix() {
        return matrix;
    }
}