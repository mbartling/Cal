package com.example.michael.cal;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SensorEventListener, AccelFragment.AccelerometerInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private SensorManager mSensorManager;
    private Sensor mAccel;
    private Sensor mProximity;

    private float[] accelVals;
    private int proximityVal = 0;
    private float proxMax;

    private float epsilon = 0.0000001f;

    private AccelWindow mAccelWindow;
    private int currPosition;
    private boolean isTakingData, isWalking;
    public String timeStamp;
    DataSaverPlaceholder myDataLogger;

    private boolean isSaved;

    //Fragment mContent;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proxMax = mProximity.getMaximumRange(); //Will treat this value as binary close, not-close
            // See Android Proximity Sensor Documentation

        Log.i("STUPID DEBUG", "BULL");

        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(proximitySensorEventListener, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mAccelWindow = new AccelWindow(20);
        currPosition = 0;

        File path = new File(Environment.getExternalStorageDirectory(), "myAccelData");

        if(savedInstanceState == null) {
            myDataLogger = new DataSaverPlaceholder(path);
            timeStamp = myDataLogger.getTimeStamp();
        }
        else
        {
        //    mContent = getFragmentManager().getFragment(savedInstanceState, "mContent");
            timeStamp = savedInstanceState.getString("timeStamp");
            myDataLogger = new DataSaverPlaceholder(path, timeStamp);
        }
        isTakingData =false;
        isWalking = false;


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // FragmentManager fragmentManager = getFragmentManager();
       // fragmentManager.putFragment(outState, "mContent", mContent);
        outState.putString("timeStamp", timeStamp);

    }

/*    @Override
    public void onNavigationDrawerItemSelected(
            int position, boolean fromSavedInstanceState) {

        if (!fromSavedInstanceState) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container,
                            PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
    }*/

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {
        // update the main content by replacing fragments
        if (!fromSavedInstanceState) {
            FragmentManager fragmentManager = getFragmentManager();
            if (position == 0) {

                fragmentManager.beginTransaction()
                        .replace(R.id.container, AccelFragment.newInstance(position + 1))
                        .commit();

            } else if (position == 4) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CalNetworkFragment.newInstance(position + 1))
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
            }
        }
    }
    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY)
            {
                proximityVal = (Math.abs(sensorEvent.values[0] - proxMax) < epsilon) ? 0:1;
                float x = accelVals[0];
                float y = accelVals[1];
                float z = accelVals[2];
                if(currPosition == 1) {

                    TextView pView = (TextView) findViewById(R.id.prox_val);


                    pView.setText(String.valueOf(proximityVal));

                    int d_isWalking     = (isWalking) ? 1 : 0;
                    int d_isTakingData  = (isTakingData) ? 1 : 0;
                    String s = String.format("%f, %f, %f, %d, %d, %d;\n", x, y, z, proximityVal, d_isWalking, d_isTakingData);
                    myDataLogger.writeData(s);

                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);

                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
        currPosition = number;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /* Accelerometer Stuff */

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something
    }


    @Override
    public final void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accelVals = event.values;
        }

        float x = accelVals[0];
        float y = accelVals[1];
        float z = accelVals[2];


        if(currPosition == 1) {
            TextView xView = (TextView) findViewById(R.id.x_val);
            TextView yView = (TextView) findViewById(R.id.y_val);
            TextView zView = (TextView) findViewById(R.id.z_val);


            xView.setText(String.valueOf(x));
            yView.setText(String.valueOf(y));
            zView.setText(String.valueOf(z));

            int d_isWalking     = (isWalking) ? 1 : 0;
            int d_isTakingData  = (isTakingData) ? 1 : 0;
            String s = String.format("%f, %f, %f, %d, %d, %d;\n", x, y, z, proximityVal, d_isWalking, d_isTakingData);
            myDataLogger.writeData(s);
            AccelReading reading = new AccelReading(x,y, z);
            if (mAccelWindow.addReading(reading) == 1) {

                double variance = mAccelWindow.variance;
                //Context context = getApplicationContext();


                //Toast toast = Toast.makeText(context, String.valueOf(variance), Toast.LENGTH_SHORT );
                //toast.show();
                TextView textView = (TextView) findViewById(R.id.var_val);
                textView.setText(String.valueOf(variance));
                //Log.i("Accel Stuff", "Variance = " + variance);
            }
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(proximitySensorEventListener, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
        mSensorManager.unregisterListener(proximitySensorEventListener);
    }

    @Override
    public void setIsTakingData(boolean state) {
        isTakingData = state;
    }

    @Override
    public void setWalkingState(boolean state) {
        isWalking = state;
        Context context = getApplicationContext();
        String walkingState;

        if (isWalking)
        {
            walkingState = "walking!";
        } else {
            walkingState = "Not Walking!";
        }
        Toast toast = Toast.makeText(context, walkingState , Toast.LENGTH_SHORT );
        toast.show();

    }


}
