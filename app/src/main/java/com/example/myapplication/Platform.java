package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.Math;

public class Platform {
    private final double G = 9.8 * 200;

    private float deltaTime;
    private GameView gameView;
    private Paint platformPaint;
    private final float width;

    private float degree;
    private Vector2 position;




    public Platform(Vector2 vector2, float width, float height, GameView gameView){
        this.position = vector2;
        this.width = width;
        degree = 0;
        this.gameView = gameView;
        deltaTime = 1 / (float)MainThread.FPS;
        platformPaint = new Paint();
        platformPaint.setColor(Color.rgb(255, 255, 255));
        platformPaint.setStyle(Paint.Style.FILL);
        platformPaint.setStrokeWidth(height);
    }

    public void draw(Canvas canvas){
        canvas.getWidth();
        canvas.getHeight();
        float deltaW1 = (width / 2) * (float) Math.cos(Math.toRadians(degree));
        float deltaW2 = (width / 2) * (float) Math.cos(Math.toRadians(degree + 180));
        float deltaH1 = (width / 2) * (float) Math.sin(Math.toRadians(degree));
        float deltaH2 = (width / 2) * (float) Math.sin(Math.toRadians(degree + 180));
        canvas.drawLine(position.x + deltaW1,position.y - deltaH1, position.x + deltaW2, position.y - deltaH2, platformPaint );
    }

    public void update() {
        degree++;
    }
}
