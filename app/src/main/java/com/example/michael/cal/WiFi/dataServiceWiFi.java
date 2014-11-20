package com.example.michael.cal.WiFi;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.michael.cal.CalNetwork.PostData;
import com.example.michael.cal.CalSQL.CalSQLObj;
import com.example.michael.cal.CalSQL.CalSqlAdapter;
import com.example.michael.cal.MainActivity;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class dataServiceWiFi extends Service {
    private IBinder mBinder = new dataBinder();

    private String GoogleAccountEmail;

    WifiManager mWifiManager;
    WifiScanReceiver mWifiScanReceiver;

    private final int numSeconds = 5;

    public dataServiceWiFi() {
    }

    public void startScan(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWifiManager.startScan();
                startScan();
            }
        }, numSeconds*1000);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiScanReceiver = new WifiScanReceiver();
        registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mWifiScanReceiver);
        super.onDestroy();
    }

    public class dataBinder extends Binder {
        dataServiceWiFi getService() {
            return dataServiceWiFi.this;
        }
    }
    public String getGoogleAccountEmail() {
        if (GoogleAccountEmail == null) {
            AccountManager am = (AccountManager) this.getSystemService(this.ACCOUNT_SERVICE);
            Account[] accounts = am.getAccounts();
            for (Account a : accounts) {
                if (a.type.equals("com.google")) {
                    GoogleAccountEmail = a.name;
                    return GoogleAccountEmail;
                }
            }
            return null;
        }
        return GoogleAccountEmail;
    }

    public void submitData(List<ScanResult> cso) {
                      //Submits localDatabase to server
        String json = createJSONObjWithEmail(cso).toString();
        Log.i("DataBase", "Attempting to send "+Integer.toString(json.split("\\}").length - 1)+" entries");
        try {
            HttpResponse httpr = new PostData.PostDataTask().execute(new PostData.PostDataObj("http://grantuy.com/cal/insert_wifi.php", json)).get();
            if (httpr != null) {
                if (httpr.getStatusLine().getStatusCode() == 200) {
                    Log.i("DataBase", "Sending Successful");                                        //Request successful
                    //clearDatabase();
                    return;
                }
            }
            Log.i("DataBase:", "There was a problem with this HTTP requst.");                       //Request failed

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public JSONArray createJSONObjWithEmail(List<ScanResult> WiFiScanResults) {
        JSONArray ja = new JSONArray();
        if (!WiFiScanResults.isEmpty()) {
            for (ScanResult result : WiFiScanResults) {
                //Output format: email, timestamp, xVal, yVal, zVal, proxVal, luxVal, isWalking, isTraining

                JSONObject jo = new JSONObject();
                try {
                    jo.put("email", getGoogleAccountEmail());
                    jo.put("SSID", result.SSID);
                    jo.put("BSSID", result.BSSID);
                    jo.put("channel", result.frequency);
                    jo.put("RSSI", result.level);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ja.put(jo);
            }
        }
        return ja;
    }

    class WifiScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> mScanResults = mWifiManager.getScanResults();
            submitData(mScanResults);
        }
    }

}
