package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.MotionEvent;
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
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null){
            ball.draw(canvas);
            platform.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (platform.haveCollisionWithBall(new Vector2(event.getX(), event.getY()), 10)) {
            reset();
        }
        return true;
    }

    public void onAcceleratorSensor(float[] values, double time) {
        if (platform != null)
            platform.onAcceleratorSensorChange(values, time);
    }

    public void onGyroscopeChange(float[] values, double time) {
        if (platform != null)
            platform.onGyroscopeChange(values, time);
    }

    private void reset() {
        ball.reset(new Vector2(width / 2, height / 10));
        platform.reset(new Vector2(width / 2, 9 * height / 10));
    }
}
