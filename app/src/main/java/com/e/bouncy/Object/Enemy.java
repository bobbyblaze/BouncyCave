package com.e.bouncy.Object;
import android.content.Context;

import com.e.bouncy.GamePanel;
import com.e.bouncy.MainThread;

public class Enemy extends Bitmaps{

    private static final int SPEED_PIXELS_PER_SECOND = 500;
    private final int MAX_SPEED = SPEED_PIXELS_PER_SECOND / MainThread.MAX_FPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = 30/SPAWNS_PER_SECOND;
    private static double updateUntilNextSpawn = UPDATES_PER_SPAWN;
    public int scrHeight;
    public int scrWidth;

    GamePanel gamePanel;

    public Enemy(Context context, int positionX, int positionY, int scrHeight, int scrWidth, int i) {
        super(context, positionX, positionY, scrHeight, scrWidth, i);

        this.i = i;
        this.scrHeight = scrHeight;
        this.scrWidth = scrWidth;
    }

    public Enemy(Context context, Bitmaps bitmaps, int positionX, int positionY, int scrWidth, int scrHeight){

        super(
                context,
                positionX = (int)(Math.random()*1000),
                positionY,
                scrWidth,
                scrHeight,
                9
        );

        this.positionY = positionY;
        this.scrHeight = scrHeight;
        this.scrWidth = scrWidth;

    }

    // checks if new enemy is ready to spawn according to decided number of spawns per minute
    public static boolean readyToSpawn() {
        if( updateUntilNextSpawn <= 0){
            updateUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updateUntilNextSpawn --;
            return false;
        }
    }

    public double getObstacleSize(){
        double size = bitmaps[i].getHeight()/2;
        return size;
    }

    public void advancing(int x){
        if((x % 100) == 0){
            velocityY+=1;
        }
    }

    @Override
    public void update() {
        //calculate vector from enemy to the bottom of the screen
        int distanceToBottom = positionY - scrHeight;

        // set velocity in the direction of the bottom of the screen
        if(distanceToBottom < 0){
            velocityY = MAX_SPEED;
        } else {
            velocityY = 0;
            positionY = 0;
        }

        //update the position of the enemy
        if(positionY == 0) {
            positionX = (int)(Math.random()*(screenWidth-bitmaps[4].getWidth()));
        }

        positionY += velocityY;
    }

}
