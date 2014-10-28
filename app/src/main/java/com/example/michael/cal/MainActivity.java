package com.example.michael.cal;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.example.michael.cal.CalSQL.CalSqlAdapter;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AccelFragment.AccelerometerInterface{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static CalSqlAdapter calSqlAdapter;

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
    //CalSqlAdapter calSqlAdapter;

    public static CalSqlAdapter getAdapter(){
        return calSqlAdapter;
    }

    public static CalSqlAdapter setAdapter(CalSqlAdapter c){
       return calSqlAdapter = c;
    }

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


        currPosition = 0;

        isTakingData =false;
        isWalking = false;

        //calSqlAdapter = new CalSqlAdapter(this);

        //SQLiteDatabase sensorDatabase = calSqlAdapter.getWritableDatabase();
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
                        .replace(R.id.container, TrainingFragment.newInstance(position + 1))
                        .commit();
            } /*else if (position == 1) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DBDevFrag.newInstance(position + 1))
                        .commit();
            }*/ else if (position == 4) {
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




    @Override
    protected void onResume(){
        super.onResume();


    }

    @Override
    protected void onPause(){
        super.onPause();

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
