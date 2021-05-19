package com.e.bouncy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    Bitmap bitmap, bitmap2, bitmap3, bitmapCopy, bitmapCopy2, bitmapCopy3, copy, copy2, copy3;

    int width;
    int height;
    boolean copyFirst;
    long speed;

    int yConnect;
    int startX;
    int endX;

    Background(Context context, int screenWidth, int screenHeight, String bitmapName, int sX, int eX, int s){

        // make a resource id out of the string of the file name
        int layer1 = context.getResources().getIdentifier("layer1", "drawable", context.getPackageName());
        int layer2 = context.getResources().getIdentifier("layer2", "drawable", context.getPackageName());
        int layer3 = context.getResources().getIdentifier("layer3", "drawable", context.getPackageName());

        // loading bitmap with id
        bitmap = BitmapFactory.decodeResource(context.getResources(), layer1);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), layer2);
        bitmap3 = BitmapFactory.decodeResource(context.getResources(), layer3);

        // making sure regular background will go first
        copyFirst = false;

        yConnect = 0;

        // position background vertically
        startX = sX * (screenWidth / 100);
        endX = eX * (screenWidth / 100);
        speed = s;

        //create bitmap
        bitmap = Bitmap.createScaledBitmap(bitmap, (endX - startX), screenHeight, true);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, (endX - startX), screenHeight, true);
        bitmap3 = Bitmap.createScaledBitmap(bitmap3, (endX - startX), screenHeight, true);

        width = bitmap.getWidth();
        width = bitmap2.getWidth();
        width = bitmap3.getWidth();
        height = bitmap.getHeight();
        height = bitmap2.getHeight();
        height = bitmap3.getHeight();

        bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        bitmapCopy2 = bitmap2.copy(bitmap2.getConfig(), true);
        bitmapCopy3 = bitmap3.copy(bitmap3.getConfig(), true);

        copy = Bitmap.createBitmap(bitmapCopy, 0,0, width, height);
        copy2 = Bitmap.createBitmap(bitmapCopy2, 0, 0, width, height);
        copy3 = Bitmap.createBitmap(bitmapCopy3, 0, 0, width, height);

    }

    public void update(){
        yConnect -= speed;

        if (yConnect >= height){
            yConnect = 0;
            copyFirst = !copyFirst;
        } else if (yConnect <=0) {
            yConnect = height;
            copyFirst = !copyFirst;
        }
    }
}
