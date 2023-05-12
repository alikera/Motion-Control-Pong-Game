package com.example.myapplication;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public final static int FPS = 100;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }

    @Override
    public void run() {
        while (running){
            try {
                sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch (Exception e){}
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e){e.printStackTrace();}
                }
            }
        }
    }
}
