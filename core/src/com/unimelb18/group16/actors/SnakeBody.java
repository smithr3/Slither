package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.unimelb18.group16.utils.SharedData;

public class SnakeBody {
    public Texture imgBody;

    private Rectangle bounds;

    private ShapeRenderer shapeRenderer;

    private int currentColor = 0;

    private Color snakeColor[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK, Color.GRAY, Color.GREEN};

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float x;
    private float y;

    public Texture getSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBodyTexture(String skin) {
        this.snakeBody = new Texture(skin);
    }

    public Texture snakeBody;

    private int radius;

    public SnakeBody(float x, float y, String skin) {
        this.x = x;
        this.y = y;
        snakeBody = new Texture(skin);

        String currentSkin = SharedData.getKey("currentSkin");

        if (currentSkin != null && !currentSkin.equals("")) {
            currentColor = Integer.parseInt(currentSkin);
        }

        this.shapeRenderer = shapeRenderer;

    }

    public void moveTowards(double targetX, double targetY, double speed) {
        double dx = x - targetX;
        double dy = y - targetY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist > 1) {
            x -= dx / speed;
            y -= dy / speed;
        }
    }
}