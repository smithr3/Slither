package com.unimelb18.group16.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SnakeBody extends Actor {
    public Texture imgBody;

    private Rectangle bounds;

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    private float x;
    private float y;

    private int radius;

    public SnakeBody(Rectangle bounds, float startX, float startY) {

        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);

        x = startX;
        y = startY;

        imgBody = new Texture("circle.png");

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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(imgBody, x, y);
    }

}