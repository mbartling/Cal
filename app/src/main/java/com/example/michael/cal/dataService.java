package com.example.michael.cal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.example.michael.cal.CalNetwork.PostData;
import com.example.michael.cal.CalSQL.CalSqlAdapter;
import com.example.michael.cal.CalSQL.CalSQLObj;

import org.apache.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class dataService extends Service {
    private IBinder mBinder = new dataBinder();
    CalSqlAdapter calSqlAdapter;

    public dataService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        calSqlAdapter = MainActivity.getAdapter();
        if(calSqlAdapter==null)
            calSqlAdapter = MainActivity.setAdapter(new CalSqlAdapter(this));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class dataBinder extends Binder {
        dataService getService() {
            return dataService.this;
        }
    }

    public void submitData() {
        //Submits localDatabase to server
        CalSQLObj[] cso = calSqlAdapter.getRangeData(0, System.currentTimeMillis());
        String json = calSqlAdapter.createJSONObjWithEmail(cso).toString();
        Log.i("Pushing to remote SQL Server. JSON Count", Integer.toString(json.split("\\}").length - 1));
        try {
            HttpResponse httpr = new PostData.PostDataTask().execute(new PostData.PostDataObj("http://grantuy.com/cal/insert.php", json)).get();
            if (httpr != null) {
                if (httpr.getStatusLine().getStatusCode() == 200) {
                    //Request successful
                    calSqlAdapter.delDbData();
                    return;
                }
            }
            //Request failed
            Log.i("HTTP:", "There was a problem with this HTTP requst.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void clearDatabase(){
        //Prints size of database (rows)
        //clears local database
        Log.i("DataBase", "SIZE "+ Integer.toString(calSqlAdapter.getDbSize()));
        calSqlAdapter.delDbData();
        Log.i("DataBase", "Deleted");
    }

}
