package com.example.myapplication;

import android.graphics.Canvas;

public interface Ball {
    void draw(Canvas canvas);

    void update();

    void reset(Vector2 vector);
    void onAcceleratorSensorChange(float[] values, double time);

    void onGyroscopeChange(float[] values, double time);
}
