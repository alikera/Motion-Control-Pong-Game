package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public final float width;
    public final float height;
    private MainThread thread;
    private Ball ball;
    private Platform platform;

    public GameView(Context context, float width, float height) {
        super(context);
        getHolder().addCallback(this);

        this.height = height;
        this.width = width;


        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        platform = new Platform(new Vector2(width / 2, 9 * height / 10), width / 3, 25, this);
        ball = new Ball(new Vector2(width / 2, height / 10), 50, this, platform);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        ball.update();
        platform.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null){
            ball.draw(canvas);
            platform.draw(canvas);
        }
    }
}
