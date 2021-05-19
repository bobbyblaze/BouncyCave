package com.e.bouncy.Object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class Bitmaps extends GameObject {

    protected Bitmap bitmap;
    protected Bitmap[] bitmaps = new Bitmap[10];

    public int i;

    protected Paint paint;

    Bitmaps(Context context, int positionX, int positionY, int screenWidth, int screenHeight, int i){
        super(positionX, positionY, screenHeight, screenWidth);

        this.i = i;

        int kamen = context.getResources().getIdentifier("kamen", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), kamen);
        bitmaps[9] = Bitmap.createScaledBitmap(bitmap, screenWidth/6, screenHeight/13, true);

        /**int stalaktit = context.getResources().getIdentifier("stalaktit", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), stalaktit);
        bitmaps[5] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/13, true);*/

        /**int women = context.getResources().getIdentifier("women", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), women);
        bitmaps[7] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);*/

        int miner = context.getResources().getIdentifier("miner", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), miner);
        bitmaps[0] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);

        int smilie = context.getResources().getIdentifier("smilie", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), smilie);
        bitmaps[1] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);

        int cehun = context.getResources().getIdentifier("cehun", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), cehun);
        bitmaps[2] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);

        int sk = context.getResources().getIdentifier("sk", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), sk);
        bitmaps[3] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);

        int cat = context.getResources().getIdentifier("cat", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), cat);
        bitmaps[4] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);

        int yoda = context.getResources().getIdentifier("yoda", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), yoda);
        bitmaps[5] = Bitmap.createScaledBitmap(bitmap, screenWidth/9, screenHeight/16, true);

    }


    public static boolean isColliding(Enemy next, Player player) {
        double distance = getDistanceBetweenObjects(next, player);
        double distanceToCollision = next.getObstacleSize() + player.getPlayerSize();
        if(distance < distanceToCollision)
            return true;
         else
            return false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmaps[i], positionX, positionY, paint);
    }

}
