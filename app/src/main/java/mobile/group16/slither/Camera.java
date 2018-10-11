package mobile.group16.slither;

public class Camera {

    // centre of view in game world space
    private float x;
    private float y;

    // 2 = magnified 2 times
    // 0.5 = zoomed out
    private float scale;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
        this.scale = 1;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void zoomOut(int steps) {
        scale *= Math.pow(0.99, steps);
    }
}
