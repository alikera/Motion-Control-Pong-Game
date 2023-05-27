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
    private GameView gameView;

    private double speed;
    private double acceleration;
    private int width;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Sensor rotationSensor;
    private long timeStamp;
    private long timeStamp2;
    private final boolean isExtra = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init_window();
        init_sensor();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            if (timeStamp == 0) timeStamp = event.timestamp;
            double deltaTime = (double) (event.timestamp - timeStamp ) / 1000000000;
            timeStamp = event.timestamp;
            gameView.onAcceleratorSensor(event.values, deltaTime);
        }
        else {
            if (timeStamp2 == 0) timeStamp2 = event.timestamp;
            double deltaTime = (double) (event.timestamp - timeStamp2 ) / 1000000000;
            timeStamp2 = event.timestamp;
            gameView.onGyroscopeChange(event.values, deltaTime);
        }
        timeStamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void init_window() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        gameView = new GameView(this, displayMetrics.widthPixels, displayMetrics.heightPixels, isExtra);
        setContentView(gameView);
    }

    private void init_sensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

}