package com.e.bouncy.Object;

import android.graphics.Canvas;

public abstract class GameObject {

    protected int positionX;
    protected int positionY;
    protected int velocityX;
    protected int velocityY;
    protected int screenHeight;
    protected int screenWidth;

    public GameObject(int positionX, int positionY, int screenHeight, int screenWidth){
        this.positionX = positionX;
        this.positionY = positionY;
        this.screenHeight = screenHeight;
        this. screenWidth = screenWidth;
    }

    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2){
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }
}
