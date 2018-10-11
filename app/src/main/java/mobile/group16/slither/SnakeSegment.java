package mobile.group16.slither;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ScaleDrawable;

public class SnakeSegment {

    private Drawable drawable;

    private double x;
    private double y;

    private float radius;

    public SnakeSegment(Context context, double startX, double startY, int radius) {
//        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{context.getResources().getColor(R.color.redStartColor), context.getResources().getColor(R.color.redEndColor)});
//        g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
//        g.setGradientRadius(100.0f);
//        g.setShape(GradientDrawable.OVAL);
//        g.setGradientCenter(0.0f, 0.45f);
//        drawable = new ScaleDrawable(g, 0, playerWidth, playerHeight).getDrawable();
//        this.playerHeight = playerHeight;
//        this.playerWidth = playerWidth;
//
//        if (drawable != null) {
//            drawable.setBounds(x, y, playerWidth, playerHeight);
//        }
        x = startX;
        y = startY;
        this.radius = radius;
    }

    public void moveTowards(double targetX, double targetY, double speed) {
        double dx = x - targetX;
        double dy = y - targetY;
        double dist = Math.sqrt(dx*dx + dy*dy);
        if (dist > 1) {
            x -= dx/speed;
            y -= dy/speed;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawCircle((int)x, (int)y, (int)radius, paint);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void fatten(float amount) {
        radius *= amount;
    }

//    public Drawable getDrawable() {
//        if (drawable != null)
//            drawable.setBounds(x, y, playerWidth, playerHeight);
//        return drawable;
//    }

}
