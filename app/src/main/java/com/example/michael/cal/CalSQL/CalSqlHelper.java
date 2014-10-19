package com.example.michael.cal.CalSQL;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by michael on 10/19/14.
 */
public class CalSqlHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME   = "cal.db";
    private static final String TABLE_NAME      = "NthSense";
    private static final String UID             = "_id"; //The UID will actually be the nanosecond
                                                         // Sensor event (should last for 200+ years)


    private static final String ENTRY_X         = "xVal";
    private static final String ENTRY_Y         = "yVal";
    private static final String ENTRY_Z         = "zVal";
    private static final String ENTRY_P         = "proximity";
    private static final String ENTRY_LUX       = "lux";

    private static final String ENTRY_IS_TRAINING_DATA  = "isTrainingData";
    private static final String ENTRY_IS_WALKING        = "Walking";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " (" +
            UID   + " INTEGER PRIMARY KEY,"+
            ENTRY_X     + " REAL, " +
            ENTRY_Y     + " REAL, " +
            ENTRY_Z     + " REAL, " +
            ENTRY_P     + " REAL, " +
            ENTRY_LUX   + " REAL, " +
            ENTRY_IS_WALKING + " INTEGER, " +
            ENTRY_IS_TRAINING_DATA + " INTEGER " +
            ");";
    private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " IF EXISTS;";
    private static int DATABASE_VERSION = 1;

    private Context context;
    CalSqlHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //CREATE TABLE NthSense (_id INTEGER PRIMARY KEY, ...);
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            DATABASE_VERSION = newVersion;
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
