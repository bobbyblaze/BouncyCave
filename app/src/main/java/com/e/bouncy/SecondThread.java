package com.e.bouncy;

import android.view.SurfaceHolder;

public class SecondThread extends Thread {

    GamePanel gamePanel;
    private long lastTime;
    private double fps;
    private SurfaceHolder surfaceHolder;
    public Thread gameThread = null;
    private volatile boolean running;

    public void setRunning(boolean running){
        this.running = running;
    }

    public SecondThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        while (running){
            lastTime = System.nanoTime();

            try{
                gamePanel.update();
                Thread.sleep(1000);
            }
            catch (InterruptedException e){}
            fps = 1000000000.0 / (System.nanoTime() - lastTime); //one second(nano) divided by amount of time it takes for one frame to finish
            lastTime = System.nanoTime();
        }
    }
    public long fps(){
        long fps = Math.round(this.fps);
        return fps;
    }
}