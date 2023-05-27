package com.example.myapplication;

import android.graphics.Canvas;

public interface Platform {
    double getDegree();
    boolean haveCollisionWithBall(Vector2 point, double radius);

    void draw(Canvas canvas);

    void reset(Vector2 vector2);

    void onAcceleratorSensorChange(float[] values, double time);

    void onGyroscopeChange(float[] values, double time);
}
