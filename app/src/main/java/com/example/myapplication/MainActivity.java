package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements SensorEventListener {

    public static double positionX;

    private double speed;
    private double acceleration;
    private long timeStamp;

    private int width;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        width = displayMetrics.widthPixels;
        setContentView(new GameView(this, displayMetrics.widthPixels, displayMetrics.heightPixels));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (timeStamp == 0) timeStamp = event.timestamp;
        double deltaTime = (double) (event.timestamp - timeStamp ) / 1000000000;
        if (Math.abs(event.values[0]) < 0.05)
            event.values[0] = 0;
        acceleration = event.values[0] * 0.8 + acceleration * 0.2;
        positionX += (0.5 * -acceleration * deltaTime * deltaTime + speed * deltaTime) * 2 * width;
        positionX = positionX > width ? width : positionX;
        positionX = positionX < 0 ? 0 : positionX;
        speed += acceleration * deltaTime;
        boolean negative = speed < 0;
        double temp = Math.abs(speed) - 0.1;
        speed = (temp < 0 ? 0 : temp) * (negative ? -1 : 1);
        timeStamp = event.timestamp;
        Log.i("speed", String.valueOf(speed));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}