package mobile.group16.slither;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class ChangeSkinView extends View {

    private Bitmap boomBitmap;
    private Paint mBackgroundPaint;
    private Paint linePaint;
    SharedData sharedData;

    int snakeColor[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.WHITE, Color.GRAY, Color.GREEN};

    public int getCurrentColor() {
        return currentColor;
    }

    int currentColor = 0;

    private Snake snake;


    public ChangeSkinView(Context context) {
        super(context);
        sharedData = new SharedData(context);
        init();
    }

    public ChangeSkinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sharedData = new SharedData(context);
        init();
    }

    public ChangeSkinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sharedData = new SharedData(context);
        init();
    }

    public ChangeSkinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        sharedData = new SharedData(context);
        init();
    }

    private void init() {
        initDrawingTools();
    }

    private void initDrawingTools() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFilterBitmap(true);

        String currentSkin = sharedData.getKey("currentSkin");

        if (currentSkin != null && !currentSkin.equals("")) {
            currentColor = Integer.parseInt(currentSkin);
        }


        mBackgroundPaint.setColor(snakeColor[currentColor]);
        linePaint = new Paint();
        linePaint.setColor(snakeColor[currentColor]);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(20);

        //snake = new Snake();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        boomBitmap = scaleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.boom), 200, 200);


    }

    public void changeColor(int side) {
        switch (side) {
            case Constants.LeftChangeSkin:
                currentColor--;
                if (currentColor < 0) {
                    currentColor = snakeColor.length - 1;
                }
                break;
            case Constants.RightChangeSkin:
                currentColor++;
                if (currentColor >= snakeColor.length) {
                    currentColor = 0;
                }
                break;
        }

        mBackgroundPaint.setColor(snakeColor[currentColor]);
        linePaint.setColor(snakeColor[currentColor]);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        int centerWidth = getWidth() / 2;

        int centerHeight = getHeight() / 2;

        canvas.drawLine(centerWidth - 240, centerHeight, centerWidth + 250, centerHeight, linePaint);
        canvas.drawCircle(centerWidth + 250, centerHeight, 20, mBackgroundPaint);

    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
}
