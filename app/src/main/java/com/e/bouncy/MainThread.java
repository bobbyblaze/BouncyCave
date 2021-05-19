package com.e.bouncy;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.e.bouncy.Menu.MainMenu;

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    public long averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private volatile boolean running;
    public static Canvas canvas;
    private MainMenu activity;

    public void setRunning(boolean running){
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try {
                gamePanel.update();
                gamePanel.draw();
            } catch (Exception e){
                e.printStackTrace();
            }

            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try {
                if(waitTime > 0){
                    this.sleep(waitTime);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("Background fps: " + averageFPS);
            }
        }
    }
}
