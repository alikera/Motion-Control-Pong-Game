package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;

import java.lang.Math;

public class Platform {
    private final double G = 9.8 * 200;

    private SensorManager sensorManager;

    private float deltaTime;
    private GameView gameView;
    private final Paint platformPaint;
    private final float width;
    private final float height;

    private float degree;
    private Vector2 position;


    public float getDegree(){
        return degree;
    }

    public Platform(Vector2 vector2, float width, float height, GameView gameView) {
        this.position = vector2;
        this.width = width;
        this.height = height;
        degree = -45;
        this.gameView = gameView;
        deltaTime = 1 / (float) MainThread.FPS;
        platformPaint = new Paint();
        platformPaint.setColor(Color.rgb(255, 255, 255));
        platformPaint.setStyle(Paint.Style.FILL);
        platformPaint.setStrokeWidth(height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean haveCollisionWithBall(Vector2 point, double radius) {
        double a = -Math.tan(Math.toRadians(degree));
        double b = 1;
        double c = position.y + position.x * Math.tan(Math.toRadians(degree));
        double distance = Math.abs(point.x * a - point.y * b + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double diameter = Math.sqrt(Math.pow(point.x - position.x, 2) + Math.pow(point.y - position.y, 2));
        double distance2 = Math.sqrt(Math.pow(diameter, 2) - Math.pow(distance, 2));
        return (distance <= radius + height / 2 && distance2 <= width / 2) ||
                (distance <= height / 2 && distance2 <= radius + width / 2);
    }

    public void draw(Canvas canvas) {
        canvas.getWidth();
        canvas.getHeight();
        float deltaW1 = (width / 2) * (float) Math.cos(Math.toRadians(degree));
        float deltaW2 = (width / 2) * (float) Math.cos(Math.toRadians(degree + 180));
        float deltaH1 = (width / 2) * (float) Math.sin(Math.toRadians(degree));
        float deltaH2 = (width / 2) * (float) Math.sin(Math.toRadians(degree + 180));
        canvas.drawLine((float) (position.x + deltaW1), (float) (position.y - deltaH1), (float) (position.x + deltaW2), (float) (position.y - deltaH2), platformPaint);
    }

    public void update() {
        position.x = MainActivity.positionX;
    }
}
