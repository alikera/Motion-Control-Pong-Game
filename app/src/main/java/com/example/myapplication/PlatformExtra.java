package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.util.Log;

import java.lang.Math;
import java.util.Vector;

public class PlatformExtra implements Platform {
    private final double G = 9.8 * 200;
    private final double M = 0.1;

    private SensorManager sensorManager;
    private float deltaTime;
    private GameView gameView;
    private final Paint platformPaint;
    private final float width;
    private final float height;
    private double[] degrees;
    private Vector2 position;
    private double velocity;
    private double acceleration;


    public double getDegree(){
        return degrees[2];
    }

    public PlatformExtra(Vector2 vector2, float width, float height, GameView gameView) {
        this.position = vector2;
        this.width = width;
        this.height = height;
        degrees = new double[]{0, 0, 0};
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
        double a = -Math.tan(Math.toRadians(degrees[2]));
        double b = 1;
        double c = position.y + position.x * Math.tan(Math.toRadians(degrees[2]));
        double distance = Math.abs(point.x * a - point.y * b + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double diameter = Math.sqrt(Math.pow(point.x - position.x, 2) + Math.pow(point.y - position.y, 2));
        double distance2 = Math.sqrt(Math.pow(diameter, 2) - Math.pow(distance, 2));
        return (distance <= radius + height / 2 && distance2 <= width / 2) ||
                (distance <= height / 2 && distance2 <= radius + width / 2);
    }

    public void draw(Canvas canvas) {
        canvas.getWidth();
        canvas.getHeight();
        float deltaW1 = (width / 2) * (float) Math.cos(Math.toRadians(-degrees[1]));
        float deltaW2 = (width / 2) * (float) Math.cos(Math.toRadians(-degrees[1] + 180));
        float deltaH1 = (width / 2) * (float) Math.sin(Math.toRadians(-degrees[1]));
        float deltaH2 = (width / 2) * (float) Math.sin(Math.toRadians(-degrees[1] + 180));
        canvas.drawLine((float) (position.x + deltaW1), (float) (position.y - deltaH1), (float) (position.x + deltaW2), (float) (position.y - deltaH2), platformPaint);
    }

    public void reset(Vector2 vector2) {
        position = vector2;
        velocity = 0;
        acceleration = 0;
        for (int i = 0; i < 3; i++) {
            degrees[i] = 0;
        }
    }

    public void onAcceleratorSensorChange(float[] values, double time) {
//        double new_acceleration = values[0] * Math.cos(Math.toRadians(degrees[1])) * Math.cos(Math.toRadians(degrees[2]))
//                + values[1] * Math.sin(Math.toRadians(degrees[1])) * Math.sin(Math.toRadians(degrees[2]))
//                + values[2] * Math.sin(Math.toRadians(degrees[1])) * Math.cos(Math.toRadians(degrees[2]));
////        double new_acceleration = values[0];
//
//        acceleration = new_acceleration * 0.2 + acceleration * 0.8;
//        boolean signEquality = ((acceleration < 0) == (velocity < 0));
//        position.x += (0.5 * acceleration * time * time + velocity * time) * 2 * gameView.width;
//        velocity += acceleration * time;
//        if (position.x < 0){
//            position.x = 0;
//            velocity = 0;
//        }
//        if (position.x > gameView.width){
//            position.x = gameView.width;
//            velocity = 0;
//        }

    }

    public void onGyroscopeChange(float[] values, double time) {
        for (int i = 0; i < 3; i++) {
            if (Math.abs(values[i]) < 0.1) continue;
            degrees[i] += Math.toDegrees(values[i]) * time;
        }
        acceleration = G * Math.sin(Math.toRadians(degrees[1])) *  0.003;
        position.x += (0.5 * acceleration * time * time + velocity * time) * 2 * gameView.width;
        velocity += acceleration * time;
        if (position.x < 0){
            position.x = 0;
            velocity = 0;
        }
        if (position.x > gameView.width){
            position.x = gameView.width;
            velocity = 0;
        }
        Log.i("accr", Double.toString(degrees[1]));
    }
}
