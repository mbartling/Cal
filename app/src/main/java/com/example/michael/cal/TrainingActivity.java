package com.example.michael.cal;

/**
 * Created by Anthony on 10/30/2014.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    TextView text;
    Button button;
    DecimalFormat time;
    long startTime,currTime;
    ImageView image;

    boolean start = false;

    public static CalSqlAdapter getAdapter(){
        return calSqlAdapter;
    }
    public static CalSqlAdapter setAdapter(CalSqlAdapter c){
        return calSqlAdapter = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        text = (TextView) findViewById(R.id.timeCounter);
        button = (Button) findViewById(R.id.training_button);
        image = (ImageView) findViewById(R.id.imageView);
        Intent intent = new Intent(this, NthSense.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        time = new DecimalFormat(".#");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime=System.currentTimeMillis();
                start = true;
                image.setImageResource(R.drawable.phoneondesk);
                button.setVisibility(View.GONE);
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
                        if(start) {
                            text.setText("Time: "+time.format((float)(currTime-startTime)/1000)+"s");
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
