package com.example.michael.cal;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.example.michael.cal.CalNetwork.PostData;
import com.example.michael.cal.CalSQL.CalSqlAdapter;
import com.example.michael.cal.CalSQL.CalSQLObj;
import java.util.concurrent.ExecutionException;

public class dataService extends Service implements SensorEventListener {
    private IBinder mBinder = new dataBinder();
    CalSqlAdapter calSqlAdapter;

    public dataService() {
        Log.i("Grant","constructor2");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Grant","created2");
        calSqlAdapter = MainActivity.getAdapter();
        if(calSqlAdapter==null)
            calSqlAdapter = new CalSqlAdapter(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent s){

    }

    @Override
    public void onAccuracyChanged(Sensor s, int i){

    }

    public class dataBinder extends Binder {
        dataService getService() {
            return dataService.this;
        }
    }

    public void submitData(){
        //Submits localDatabase to server
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

    public void clearDatabase(){
        //Prints size of database (rows)
        //clears local database
        Log.i("DB SIZE", Integer.toString(calSqlAdapter.getDbSize()));
        calSqlAdapter.delDbData();
        Log.i("Deleted", "DB");
    }

}
