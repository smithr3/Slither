package mobile.group16.slither;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ScaleDrawable;

public class SnakeTrail {

    private Drawable drawable;

    //Controlling Y coordinate so that ship won't go outside the screen
    private int x;
    private int y;

    private int playerWidth;
    private int playerHeight;

    private void CreateCircle(Context context) {



    }

    public SnakeTrail(Context context, int screenX, int screenY, int playerHeight, int playerWidth) {
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{context.getResources().getColor(R.color.redStartColor), context.getResources().getColor(R.color.redEndColor)});
        g.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        g.setGradientRadius(100.0f);
        g.setShape(GradientDrawable.OVAL);
        g.setGradientCenter(0.0f, 0.45f);
        drawable = new ScaleDrawable(g, 0, playerWidth, playerHeight).getDrawable();
        this.playerHeight = playerHeight;
        this.playerWidth = playerWidth;


        if (drawable != null)
            drawable.setBounds(x, y, playerWidth, playerHeight);


        //calculating maxY
        //   maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Drawable getDrawable() {
        if (drawable != null)
            drawable.setBounds(x, y, playerWidth, playerHeight);

        return drawable;
    }

}
