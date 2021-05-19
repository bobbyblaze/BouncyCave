package com.e.bouncy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.e.bouncy.Menu.GameOver;
import com.e.bouncy.Menu.MainMenu;
import com.e.bouncy.Menu.SkinMenu;
import com.e.bouncy.Object.Bitmaps;
import com.e.bouncy.Object.Enemy;
import com.e.bouncy.Object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //other classes
    MainMenu main;
    GameOver GameOver;
    SkinMenu skins;
    Bitmaps bitmaps;
    // main thread
    private MainThread thread;
    public volatile boolean running;
    //array of bitmaps for background
    ArrayList<Background> backgrounds = new ArrayList<Background>();
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    Player player;
    Enemy enemy;
    // drawing
    private Paint paint;
    private Canvas canvas;
    public SurfaceHolder holder;
    // counters, booleans
    public int count = 0;
    private volatile int action;
    // display res
    int screenWidth, screenHeight;
    // holds a reference to the Activity
    public Context context;
    // position of player
    int playerPointX, playerPointY;
    public int pI;
    //obstacle position
    int positionY = 0, positionX;
    //alive
    int lives = 3;
    //score
    float score = 0;
    public int Score = 0;
    //bitmap
    Bitmap pause, pauseMenu, home, restart, continoue, soundOn, soundOff,
           vibrationOn, vibrationOff;
    int pauseX = 10;
    int pauseY = 10;
    boolean paused = false;
    // Vibrations
    Vibrator vibrator;
    //sound and vibrations true
    boolean VibrationOn = true, SoundOn = true;
    //background speed
    int s1 = 8, s2 = 9, s3 = 10;
    //enemy speed


    public GamePanel(Context context, int screenWidth, int screenHeight, int whichPlayer){
        super(context);

        getHolder().addCallback(this);
        paint = new Paint();
        holder = getHolder();

        //threads
        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        this.context = context;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.playerPointX = screenWidth/2-60;
        this.playerPointY = screenHeight-400;

        enemy = new Enemy(this.context, 0, 0, screenHeight, screenWidth, 1);
        skins = new SkinMenu();

        player = new Player(
                this.context,
                playerPointX,
                playerPointY,
                screenWidth,
                screenHeight,
                whichPlayer);

        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "position1", 0, 110, s1));
        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "position2", 0, 110, s2));
        backgrounds.add(new Background(
                this.context,
                screenWidth,
                screenHeight,
                "position3", 0, 110, s3));

    }

    public void score(int x, int y){
        String text1 = "Score: ";
        String text2 = "m";
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(screenWidth/20);
        if(count>0 && lives>0 && !paused){
            score += 0.3;
            Score = Math.round(score);
        }
        canvas.drawText(text1+Score+text2, x, y, paint);
    }

    public void lives(int x, int y){
        String text1 = "Lives: ";
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(screenWidth/25);
        canvas.drawText(text1+lives, x, y, paint);
    }

    public void advancing(){
        if((Score % 100) == 0){
            s1+=1;
            s2+=1;
            s3+=1;
        }
        enemy.advancing(this.Score);
    }

    public static boolean approximatelyEqual(float desiredValue, float actualValue, float tolerancePercentage) {
        float diff = Math.abs(desiredValue - actualValue);         //  1000 - 950  = 50
        float tolerance = tolerancePercentage/100 * desiredValue;  //  20/100*1000 = 200
        return diff < tolerance;                                   //  50<200      = true
    }

    public void pauseButton(int x, int y){
        int pauseBit = context.getResources().getIdentifier("pause", "drawable", context.getPackageName());
        pause = BitmapFactory.decodeResource(context.getResources(), pauseBit);
        pause = Bitmap.createScaledBitmap(pause, screenWidth/9, screenHeight/16, true);

        canvas.drawBitmap(pause, x, y, paint);
    }

    public void paused(){
        int pauMenu = context.getResources().getIdentifier("pause_menu", "drawable", context.getPackageName());
        pauseMenu = BitmapFactory.decodeResource(context.getResources(), pauMenu);
        pauseMenu = Bitmap.createScaledBitmap(pauseMenu, screenWidth-(screenWidth/6), screenHeight-(screenHeight/3), true);

        int homeBut = context.getResources().getIdentifier("home", "drawable", context.getPackageName());
        home = BitmapFactory.decodeResource(context.getResources(), homeBut);
        home = Bitmap.createScaledBitmap(home, 2*(screenWidth/9), 2*(screenHeight/18), true);

        int restartBut = context.getResources().getIdentifier("restart", "drawable", context.getPackageName());
        restart = BitmapFactory.decodeResource(context.getResources(), restartBut);
        restart = Bitmap.createScaledBitmap(restart, 2*(screenWidth/9), 2*(screenHeight/18), true);

        int soundon = context.getResources().getIdentifier("sound_on", "drawable", context.getPackageName());
        soundOn = BitmapFactory.decodeResource(context.getResources(), soundon);
        soundOn = Bitmap.createScaledBitmap(soundOn, 2*(screenWidth/11), 2*(screenHeight/22), true);

        int soundoff = context.getResources().getIdentifier("sound_off", "drawable", context.getPackageName());
        soundOff = BitmapFactory.decodeResource(context.getResources(), soundoff);
        soundOff = Bitmap.createScaledBitmap(soundOff, 2*(screenWidth/11), 2*(screenHeight/22), true);

        int vibrationon = context.getResources().getIdentifier("vibration_on", "drawable", context.getPackageName());
        vibrationOn = BitmapFactory.decodeResource(context.getResources(), vibrationon);
        vibrationOn = Bitmap.createScaledBitmap(vibrationOn, 2*(screenWidth/11), 2*(screenHeight/22), true);

        int vibrationoff = context.getResources().getIdentifier("vibration_off", "drawable", context.getPackageName());
        vibrationOff = BitmapFactory.decodeResource(context.getResources(), vibrationoff);
        vibrationOff = Bitmap.createScaledBitmap(vibrationOff, 2*(screenWidth/11), 2*(screenHeight/22), true);

        int continueBut = context.getResources().getIdentifier("continoue", "drawable", context.getPackageName());
        continoue = BitmapFactory.decodeResource(context.getResources(), continueBut);
        continoue = Bitmap.createScaledBitmap(continoue, (screenWidth-(screenWidth/3)), screenHeight/8, true);

        Paint paint = new Paint();
        canvas.drawBitmap(pauseMenu, (screenWidth-(screenWidth-(screenWidth/6)))/2, (screenHeight-(screenHeight-(screenHeight/3)))/2, paint);
        canvas.drawBitmap(continoue, screenWidth-(screenWidth-(screenWidth/6)), (screenHeight-(screenHeight/3))+(screenHeight-(screenHeight-(screenHeight/3)))/10, paint);
        canvas.drawBitmap(restart, (screenWidth/2)-(2*(screenWidth/9)), (screenHeight/2)-(2*(screenHeight/18)), paint);
        canvas.drawBitmap(home, screenWidth/2, (screenHeight/2)-(2*(screenHeight/18)), paint);
        if (SoundOn && VibrationOn) {
            canvas.drawBitmap(soundOn, (screenWidth / 2) - (2 * (screenWidth / 11)), (screenHeight / 2) + (screenHeight / 36), paint);
            canvas.drawBitmap(vibrationOn, screenWidth / 2, (screenHeight / 2) + (screenHeight / 36), paint);
        } else if (!SoundOn && VibrationOn) {
            canvas.drawBitmap(soundOff, (screenWidth / 2) - (2 * (screenWidth / 11)), (screenHeight / 2) + (screenHeight / 36), paint);
            canvas.drawBitmap(vibrationOn, screenWidth / 2, (screenHeight / 2) + (screenHeight / 36), paint);
        } else if  (SoundOn && !VibrationOn) {
            canvas.drawBitmap(soundOn, (screenWidth / 2) - (2 * (screenWidth / 11)), (screenHeight / 2) + (screenHeight / 36), paint);
            canvas.drawBitmap(vibrationOff, screenWidth / 2, (screenHeight / 2) + (screenHeight / 36), paint);
        } else if  (!SoundOn && !VibrationOn) {
            canvas.drawBitmap(soundOff, (screenWidth / 2) - (2 * (screenWidth / 11)), (screenHeight / 2) + (screenHeight / 36), paint);
            canvas.drawBitmap(vibrationOff, screenWidth / 2, (screenHeight / 2) + (screenHeight / 36), paint);
        }
        //canvas.drawBitmap(settings, (screenWidth/2)+(2*(screenWidth/11)), screenHeight/2, paint);
    }

    public void drawText(int x, int y){
        String text = "Touch the screen to start!";
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(screenWidth/20);
        //fadingTextView.setText(text);
        //fadingTextView.setTimeout(300, TimeUnit.MILLISECONDS);
        canvas.drawText(text, x, y, paint);
    }

    private void drawBackground(int position){

        // copy of the relevant background
        Background bg = backgrounds.get(position);

        // define what portion of images to capture and
        // what coordinates of screen to draw them at

        // for the regular bitmap
        Rect toRect1 = new Rect(0, 0, bg.width, bg.height - bg.yConnect);
        Rect fromRect1 = new Rect(bg.startX, bg.yConnect, bg.endX, bg.height);

        //from the reversed background
        Rect toRect2 = new Rect(0, bg.height - bg.yConnect, bg.width, bg.height);
        Rect fromRect2 = new Rect(bg.startX, 0, bg.endX, bg.yConnect);

        // draw the two background bitmaps
        if (!bg.copyFirst){
            canvas.drawBitmap(bg.bitmap, fromRect1, toRect1, paint);
            canvas.drawBitmap(bg.copy, fromRect2, toRect2, paint);
        } else {
            canvas.drawBitmap(bg.bitmap, fromRect2, toRect2, paint);
            canvas.drawBitmap(bg.copy, fromRect1, toRect1, paint);
        }
    }

    private void drawBackground2(int position){
        Background bg = backgrounds.get(position);

        // for the regular bitmap
        Rect toRect1 = new Rect(0, 0, bg.width, bg.height - bg.yConnect);
        Rect fromRect1 = new Rect(bg.startX, bg.yConnect, bg.endX, bg.height);

        //from the reversed background
        Rect toRect2 = new Rect(0, bg.height - bg.yConnect, bg.width, bg.height);
        Rect fromRect2 = new Rect(bg.startX, 0, bg.endX, bg.yConnect);

        // draw the two background bitmaps
        if (!bg.copyFirst){
            canvas.drawBitmap(bg.bitmap2, fromRect1, toRect1, paint);
            canvas.drawBitmap(bg.copy2, fromRect2, toRect2, paint);
        } else {
            canvas.drawBitmap(bg.bitmap2, fromRect2, toRect2, paint);
            canvas.drawBitmap(bg.copy2, fromRect1, toRect1, paint);
        }
    }

    private void drawBackground3(int position){
        Background bg = backgrounds.get(position);

        // for the regular bitmap
        Rect fromRect1 = new Rect(bg.startX, bg.yConnect, bg.endX, bg.height);
        Rect toRect1 = new Rect(0, 0, bg.width, bg.height - bg.yConnect);

        //from the reversed background
        Rect fromRect2 = new Rect(bg.startX, 0, bg.endX, bg.yConnect);
        Rect toRect2 = new Rect(0, bg.height - bg.yConnect, bg.width, bg.height);


        // draw the two background bitmaps
        if (!bg.copyFirst){
            canvas.drawBitmap(bg.bitmap3, fromRect1, toRect1, paint);
            canvas.drawBitmap(bg.copy3, fromRect2, toRect2, paint);
        } else {
            canvas.drawBitmap(bg.bitmap3, fromRect2, toRect2, paint);
            canvas.drawBitmap(bg.copy3, fromRect1, toRect1, paint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        try{
            thread.setRunning(false);
            thread.join();
        } catch(Exception e){
            e.printStackTrace();
        }
        canvas = holder.lockCanvas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();

        Iterator<Enemy> iteratorEnemy = enemyList.iterator();

        int continueX = screenWidth-(screenWidth-(screenWidth/6));
        int continueY = (screenHeight-(screenHeight/3))+(screenHeight-(screenHeight-(screenHeight/3)))/10;
        int continueSizeX = (screenWidth-(screenWidth/3));
        int continueSizeY = screenHeight/8;

        int rightButtonsX = screenWidth/2;
        int buttonRestartX = (screenWidth/2)-(2*(screenWidth/9));
        int buttonSoundX = (screenWidth/2)-(2*(screenWidth/11));

        int mainButtonsY = (screenHeight/2)-(2*(screenHeight/18));
        int settingButtonsY = (screenHeight/2)+(screenHeight/36);

        int mainButtonsSizeX = 2*(screenWidth/9);
        int mainButtonsSizeY = 2*(screenHeight/18);

        int settingButtonsSizeX = 2*(screenWidth/11);
        int settingButtonsSizeY = 2*(screenHeight/22);

        if(action == MotionEvent.ACTION_DOWN && count > 0 && x >= pauseX && x <= (pauseX + screenWidth/9) && y >= pauseY && y <= (pauseY + screenHeight/16)){
            paused = true;
        } else if (action == MotionEvent.ACTION_DOWN && paused && x >= continueX && x <= continueX+continueSizeX
                  && y >= continueY && y <= continueY+continueSizeY) {
            paused = false;
        } else if (action == MotionEvent.ACTION_DOWN && paused && x >= buttonRestartX && x <= (buttonRestartX + mainButtonsSizeX) && y >= mainButtonsY
                  && y <= (mainButtonsY + mainButtonsSizeY)) {
            count = 0;
            lives = 3;
            playerPointX = screenWidth/2-60;
            score = 0;
            paused = false;
            enemyList.clear();
        } else if (action == MotionEvent.ACTION_DOWN && paused && x >= rightButtonsX && x <= (rightButtonsX + mainButtonsSizeX) && y >= mainButtonsY
                && y <= (mainButtonsY + mainButtonsSizeY)) {
            surfaceDestroyed(holder);
            context.startActivity(new Intent(context, MainMenu.class));
        } else if (action == MotionEvent.ACTION_DOWN && paused && SoundOn && x >= buttonSoundX && x <= (buttonSoundX + settingButtonsSizeX) && y >= settingButtonsY
                && y <= (settingButtonsY + settingButtonsSizeY)) {
            SoundOn = false;
        } else if (action == MotionEvent.ACTION_DOWN && paused && !SoundOn && x >= buttonSoundX && x <= (buttonSoundX + settingButtonsSizeX) && y >= settingButtonsY
                && y <= (settingButtonsY + settingButtonsSizeY)) {
            SoundOn = true;
        } else if (action == MotionEvent.ACTION_DOWN && paused && VibrationOn && x >= rightButtonsX && x <= (rightButtonsX + settingButtonsSizeX) && y >= settingButtonsY
                && y <= (settingButtonsY + settingButtonsSizeY)) {
            VibrationOn = false;
        } else if (action == MotionEvent.ACTION_DOWN && paused && !VibrationOn && x >= rightButtonsX && x <= (rightButtonsX + settingButtonsSizeX) && y >= settingButtonsY
                && y <= (settingButtonsY + settingButtonsSizeY)) {
            VibrationOn = true;
            vibrateNow(100);
        } else if (action == MotionEvent.ACTION_DOWN) {
            count++;

            if (count == 0) {
                player.velocityX = -15;
            } else if ((count % 2) == 0) {
                player.velocityX = 15;
            } else if ((count % 2) != 0) {
                player.velocityX = -15;
            }
        }

        if(lives == 0) {
            //game over
            surfaceDestroyed(holder);
            context.startActivity(new Intent(context, GameOver.class));
        }

        return false;
    }

    public void vibrateNow(int millis){
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(millis);
    }

    public void update(){
        if(lives>0 && count>0 && !paused) {
            player.update();
            advancing();
            // spawn enemies if it is time to spawn new enemies
            if (Enemy.readyToSpawn() && enemyList.size()<5) {
               enemyList.add(new Enemy(getContext(), bitmaps, positionX, positionY, screenWidth, screenHeight));
            }

            // update state of each enemy
            for(Enemy enemy : enemyList){
                enemy.update();
            }

            Iterator<Enemy> iteratorEnemy = enemyList.iterator();
            while (iteratorEnemy.hasNext()){
                if (Bitmaps.isColliding(iteratorEnemy.next(), player)){
                    if(lives>1) {
                        iteratorEnemy.remove();
                        if(VibrationOn) {
                            vibrateNow(75);
                        }
                        lives--;
                    } else if (lives == 1){
                        iteratorEnemy.remove();
                        lives--;
                    }
                }
            }
        }

        if(!paused) {
            for (Background bg : backgrounds) {
                bg.update();
            }
        }
    }

    public void draw(){
        if(holder.getSurface().isValid()){
            //lock the area of memory we will be drawing to
            canvas = holder.lockCanvas();

            //draw background color
            canvas.drawColor(Color.argb(255, 0, 3, 70));

            //background 1-3 lvl parallax
            drawBackground(0);
            drawBackground2(1);
            drawBackground3(2);

            //start text
            if(count == 0) {
                drawText(screenWidth / 2, screenHeight / 2);
            }

            if(count > 0) {
                //obstacle
                for(Enemy enemy : enemyList){
                    enemy.draw(canvas);
                }

                //player
                player.draw(canvas);

                //pause button
                pauseButton(pauseX, pauseY);
                //score
                score(screenWidth/2, screenHeight/16);
                //lives
                lives(screenWidth/2, screenHeight/11);
            }

            if(lives == 0){
                String text = "Ohh, you lost...";
                String text2 = "Touch the screen and you'll";
                String text3 = "get another life to continue!";
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setAntiAlias(true);
                paint.setTextSize(screenWidth/20);
                canvas.drawText(text, screenWidth / 2, screenHeight / 2, paint);
                canvas.drawText(text2, screenWidth / 2, (screenHeight / 2)+screenWidth/20, paint);
                canvas.drawText(text3, screenWidth / 2, (screenHeight / 2)+2*(screenWidth/20), paint);
            }

            if(paused){
                paused();
            }

            //unlock and draw the scene
            holder.unlockCanvasAndPost(canvas);

        } else {
            System.out.println("drawing error");
        }

    }

    public void pause() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        running = true;
        thread.start();
    }

}