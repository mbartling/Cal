package com.example.michael.cal;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.example.michael.cal.CalSQL.CalSqlAdapter;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks, AccelFragment.AccelerometerInterface{
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static CalSqlAdapter calSqlAdapter;

    private boolean isTakingData, isWalking;
    public String timeStamp;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

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
        mNavigationDrawerFragment.setUp(                                                            // Set up the drawer.
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        isWalking = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("timeStamp", timeStamp);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {
        if (!fromSavedInstanceState) {                                                              // update the main content by replacing fragments
            FragmentManager fragmentManager = getFragmentManager();
            if (position == 0) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TrainingFragment.newInstance(position + 1))
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
            getMenuInflater().inflate(R.menu.main, menu);                                           // Only show items in the action bar relevant to this screen
            restoreActionBar();                                                                     // if the drawer is not showing. Otherwise, let the drawer
            return true;                                                                            // decide what to show in the action bar.
        }
        return super.onCreateOptionsMenu(menu);
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
