package com.example.michael.cal;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.michael.cal.CalNetwork.PostData;
import com.example.michael.cal.CalSQL.CalSqlAdapter;
import com.example.michael.cal.CalSQL.CalSQLObj;

import org.apache.http.HttpResponse;

import java.net.BindException;
import java.util.concurrent.ExecutionException;

public class NthSense extends Service implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccel;
    private Sensor mProximity;
    private Sensor mLight;

    private IBinder mBinder = new NthBinder();

    private float[] accelVals = new float[3];
    private int proximityVal = 0;
    private float proxMax;
    private float lux;

    private float epsilon = 0.0000001f;
    CalSqlAdapter calSqlAdapter;

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
        isWalking = false;
        isTakingData = false;

        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);

        calSqlAdapter = new CalSqlAdapter(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");\
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //long timeStamp = sensorEvent.timestamp;
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityVal = (Math.abs(sensorEvent.values[0] - proxMax) < epsilon) ? 0 : 1;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelVals = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            lux = sensorEvent.values[0];
        }

        float x = accelVals[0];
        float y = accelVals[1];
        float z = accelVals[2];

        //Will Bind the service giving us the ability to set isWalking and isTakingData
        int d_isWalking = (isWalking) ? 1 : 0;
        int d_isTrainingData = (isTakingData) ? 1 : 0;
        //String s = String.format("%f, %f, %f, %d, %d, %d;\n", x, y, z, proximityVal, d_isWalking, d_isTakingData);

        long timestamp = System.currentTimeMillis();
        calSqlAdapter.insertData(new CalSQLObj(x,y,z,proximityVal,lux,d_isWalking,d_isTrainingData,timestamp)); //Insert data into db



        //Below is the logging command
        /*CalSQLObj so = calSqlAdapter.getSingleData(timestamp); //Read data from the db using the recently entered timestamp as identifier and print out information using the log command below.
        Log.i("db data", "timeStamp: " + so.getTimestamp() + ", xVal: " + so.getxVal() + ", yVal: " + so.getyVal() + ", zVal: " + so.getzVal() +
        ", proxVal: " + so.getProxVal() + ", lxuVal: " + so.getLuxVal() + ", isWalking: " + so.getIsWalking() + ", isTraining: " + so.getIsTraining());
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    public class NthBinder extends Binder {
        NthSense getService() {
            return NthSense.this;
        }
    }

    public void set_is_walking(boolean is_walking)
    {
        this.isWalking = is_walking;
        Log.i("NthSense", "Setting is Walking " + String.valueOf(this.isWalking));



        //This is just test code.
        CalSQLObj[] cso = calSqlAdapter.getRangeData(0, System.currentTimeMillis());
        String json = calSqlAdapter.createJSONObjWithEmail(cso).toString();
        Log.i("JSON Count", Integer.toString(json.split("\\}").length));
        try {
            //HttpResponse httpr =
            new PostData.PostDataTask().execute(new PostData.PostDataObj("http://grantuy.com/cal/insert.php", json)).get();
            //Log.i("HTTP:",Integer.toString(httpr.getStatusLine().getStatusCode()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void set_is_takingData(boolean is_takingData)
    {
        this.isTakingData = is_takingData;
        Log.i("NthSense", "Setting is Taking Data " + String.valueOf(this.isTakingData));

        //This is just test code
        Log.i("DB SIZE", Integer.toString(calSqlAdapter.getDbSize()));
        calSqlAdapter.delDbData();
        Log.i("Deleted", "DB");
    }
}
