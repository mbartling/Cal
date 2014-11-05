package com.example.michael.cal;

/**
 * Created by Anthony on 10/30/2014.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michael.cal.CalSQL.CalSqlAdapter;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TrainingActivity extends Activity{

    private static CalSqlAdapter calSqlAdapter;
    NthSense sensorService;
    Timer T=new Timer();
    TextView text,second;
    Button button;
    DecimalFormat time;
    long startTime,currTime;
    ImageView image;
    int clickCount = 0;
    Vibrator v;

    public static CalSqlAdapter getAdapter(){
        return calSqlAdapter;
    }
    public static CalSqlAdapter setAdapter(CalSqlAdapter c){
        return calSqlAdapter = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setContentView(R.layout.activity_training);
        text = (TextView) findViewById(R.id.timeCounter);
        second = (TextView) findViewById(R.id.secondText);
        button = (Button) findViewById(R.id.training_button);
        image = (ImageView) findViewById(R.id.imageView);
        Intent intent = new Intent(this, NthSense.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        time = new DecimalFormat(".#");

        text.setText("Press the Start button and place the\n     phone on a flat surface for 10s.");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickCount==0){
                    image.setImageResource(R.drawable.phoneondesk);
                    sensorService.set_is_takingData(true);
                }
                else if(clickCount==2){
                    image.setImageResource(R.drawable.phoneinpocket);
                    image.setVisibility(View.VISIBLE);
                    second.setVisibility(View.GONE);
                    sensorService.set_is_takingData(true);
                }
                else if(clickCount==4){
                    image.setImageResource(R.drawable.walkingwithphone);
                    image.setVisibility(View.VISIBLE);
                    second.setVisibility(View.GONE);
                    sensorService.set_is_takingData(true);
                    sensorService.set_is_walking(true);
                }
                else if(clickCount==6){
                    //send app to MainActivity
                }
                button.setVisibility(View.GONE);
                clickCount++;
                startTime=System.currentTimeMillis();
            }
        });

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        currTime=System.currentTimeMillis();
                        if(clickCount==1) {
                            text.setText("Time: " + time.format((float) (currTime - startTime) / 1000) + "s");
                            if ((currTime - startTime) > 10000) {
                                sensorService.set_is_takingData(false);
                                image.setVisibility(View.GONE);
                                clickCount++;
                                button.setVisibility(View.VISIBLE);
                                v.vibrate(500);
                                text.setText("Press the start button and place the \n      phone in your pocket for 15s.");
                                second.setText("(Phone will vibrate on completion)");
                            }
                        }
                        else if(clickCount==3) {
                            text.setText("Time: "+time.format((float)(currTime-startTime)/1000)+"s");
                            if ((currTime - startTime) > 15000) {
                                sensorService.set_is_takingData(false);
                                image.setVisibility(View.GONE);
                                second.setVisibility(View.VISIBLE);
                                clickCount++;
                                button.setVisibility(View.VISIBLE);
                                v.vibrate(500);
                                text.setText("Press the Start button and with the\n        phone in your pocket walk\n                  around for 10s");
                            }
                        }
                        else if(clickCount==5) {
                            text.setText("Time: "+time.format((float)(currTime-startTime)/1000)+"s");
                            if ((currTime - startTime) > 10000) {
                                sensorService.set_is_takingData(false);
                                sensorService.set_is_walking(false);
                                image.setVisibility(View.GONE);
                                clickCount++;
                                button.setVisibility(View.VISIBLE);
                                v.vibrate(500);
                                text.setText("Press the Start button to continue to the main app");
                            }
                        }
                    }
                });
            }
        }, 50, 50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();                                                                  // Handle action bar item clicks here. The action bar will
        if (id == R.id.action_settings) {                                                           // automatically handle clicks on the Home/Up button, so long
            return true;                                                                            // as you specify a parent activity in AndroidManifest.xml.
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        unbindService(mConnection);                                                                 //Michael claims "possibly dangerous" DO NOT STORE NEAR OPEN FLAMES
        super.onDestroy();
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            NthSense.NthBinder binder = (NthSense.NthBinder) service;
            sensorService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };
}
