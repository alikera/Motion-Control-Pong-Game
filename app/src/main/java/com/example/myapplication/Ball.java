package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    private final double G = 9.8 * 200;

    private float deltaTime;
    private GameView gameView;
    private Paint ballPaint;
    private final float radius;
    private Vector2 position;

    private Vector2 speed;



    public Ball(Vector2 vector2, float radius, GameView gameView){
        this.position = vector2;
        this.radius = radius;
        this.gameView = gameView;
        deltaTime = 1 / (float)MainThread.FPS;
        ballPaint = new Paint();
        ballPaint.setColor(Color.rgb(255, 255, 255));
        ballPaint.setStyle(Paint.Style.FILL);
        speed = new Vector2(1000, 0);
    }

    public void draw(Canvas canvas){
        canvas.getWidth();
        canvas.getHeight();
        canvas.drawCircle(position.x, position.y, radius, ballPaint);
    }

    public void update() {
        position.add(speed.mul(deltaTime));
        if (position.x + radius >= gameView.width && speed.x > 0 || position.x - radius <= 0 && speed.x < 0){
            speed = speed.mul(new Vector2(-1, 1));
        }
        if (position.y + radius >= gameView.height && speed.y > 0 || position.y - radius <= 0 && speed.y < 0){
            speed = speed.mul(new Vector2(1, -1));
        }
        speed.add(new Vector2((float) 0, (float) (G * deltaTime)));
    }
}
