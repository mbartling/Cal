package com.example.michael.cal;

/**
 * Created by Anthony on 10/30/2014.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.michael.cal.CalSQL.CalSqlAdapter;

public class TrainingActivity extends Activity{

    private static CalSqlAdapter calSqlAdapter;

    public String timeStamp;

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("timeStamp", timeStamp);
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
}
