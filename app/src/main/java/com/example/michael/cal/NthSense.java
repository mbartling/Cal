package com.example.michael.cal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class NthSense extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccel;
    private Sensor mProximity;
    private Sensor mLight;


    private float[] accelVals;
    private int proximityVal = 0;
    private float proxMax;
    private float lux;

    private float epsilon = 0.0000001f;

    private boolean isWalking, isTakingData;
    public NthSense() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proxMax = mProximity.getMaximumRange(); //Will treat this value as binary close, not-close
        // See Android Proximity Sensor Documentation
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);



        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityVal = (Math.abs(sensorEvent.values[0] - proxMax) < epsilon) ? 0 : 1;
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accelVals = sensorEvent.values;
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            lux = sensorEvent.values[0];
        }

            float x = accelVals[0];
            float y = accelVals[1];
            float z = accelVals[2];

            //Will Bind the service giving us the ability to set isWalking and isTakingData
                int d_isWalking     = (isWalking) ? 1 : 0;
                int d_isTakingData  = (isTakingData) ? 1 : 0;
                String s = String.format("%f, %f, %f, %d, %d, %d;\n", x, y, z, proximityVal, d_isWalking, d_isTakingData);



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);

    }
}
