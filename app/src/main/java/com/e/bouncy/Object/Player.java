package com.e.bouncy.Object;

import android.content.Context;

import com.e.bouncy.Menu.SkinMenu;

public class Player extends Bitmaps {

    public int velocityX;
    SkinMenu skin = new SkinMenu();

    public Player(Context context, int positionX, int positionY, int screenWidth, int screenHeight, int i){
        super(context, positionX, positionY, screenWidth, screenHeight, i);

        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPlayerSize(){
        double size = bitmaps[i].getWidth()/2;
        return size;
    }

    @Override
    public void update() {

        if (velocityX == -15 && positionX > ((screenWidth/100)*6)) {

            positionX += velocityX;

        } else if (velocityX == 15 && positionX < ((screenWidth/100)*90)) {

            positionX += velocityX;

        }
    }
}
