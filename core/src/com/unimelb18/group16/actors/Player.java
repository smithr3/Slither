package com.unimelb18.group16.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    public int x,y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int dx, dy;
    public int width, height;
    public double area;
    public int hp;

    public Player(){
        hp = 100;
        width = 1150;
        height = 500;
        area = width*height;
        x = Gdx.graphics.getWidth()/2;
        y = Gdx.graphics.getHeight()/2;
        dx = 5;
        dy = 5;

    }
    public void render(ShapeRenderer shape, OrthographicCamera camera){
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        shape.circle(x, y, 32);
        shape.end();

    }
    public void update(){
        if(Gdx.input.isTouched()){
           // x -= dx;
        }
    }
    public void setPos(int x, int y){
        x = this.x;
        y = this.y;
    }


    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}