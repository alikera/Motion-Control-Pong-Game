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

    public static double rotation;

    private double speed;
    private double acceleration;
    private long timeStamp;

    private int width;
    private SensorManager sensorManager;
    private Sensor sensor;

    private Sensor rotationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        width = displayMetrics.widthPixels;
        positionX = (double) width/2;
        setContentView(new GameView(this, displayMetrics.widthPixels, displayMetrics.heightPixels));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            if (timeStamp == 0) timeStamp = event.timestamp;
            double deltaTime = (double) (event.timestamp - timeStamp ) / 1000000000;
            if (Math.abs(event.values[0]) < 0.05)
                event.values[0] = 0;
            acceleration = -event.values[0] * 0.8 + acceleration * 0.2;
            positionX += (0.5 * acceleration * deltaTime * deltaTime + speed * deltaTime) * 2 * width;
            speed += acceleration * deltaTime;
            if (positionX < 0){
                positionX = 0;
                speed = 0;
            }
            if (positionX > width){
                positionX = width;
                speed = 0;
            }

            boolean negative = speed < 0;
            double temp = Math.abs(speed) - 0.2 * deltaTime;
            speed = (temp < 0 ? 0 : temp) * (negative ? -1 : 1);
            timeStamp = event.timestamp;
            Log.i("speed", String.valueOf(speed));
        } else {
            rotation = Math.toDegrees(event.values[2]);
            Log.i("rotation", String.valueOf(rotation));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}