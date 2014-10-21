package com.example.michael.cal.CalSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by michael on 10/19/14.
 */
public class CalSqlAdapter {

    CalSqlHelper helper;

    public CalSqlAdapter(Context context) {
        helper = new CalSqlHelper(context);
    }

    public long insertData(CalSQLObj SQLObj) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CalSqlHelper.UID, SQLObj.getTimeStamp());
        contentValues.put(CalSqlHelper.ENTRY_X, SQLObj.getY());
        contentValues.put(CalSqlHelper.ENTRY_Y, SQLObj.getY());
        contentValues.put(CalSqlHelper.ENTRY_Z, SQLObj.getZ());
        contentValues.put(CalSqlHelper.ENTRY_P, SQLObj.getProximityVal());
        contentValues.put(CalSqlHelper.ENTRY_LUX, SQLObj.getLux());
        contentValues.put(CalSqlHelper.ENTRY_IS_WALKING, SQLObj.getD_isWalking());
        contentValues.put(CalSqlHelper.ENTRY_IS_TRAINING_DATA, SQLObj.getD_isTrainingData());

        long id = db.insert(CalSqlHelper.TABLE_NAME, null, contentValues);
        db.close();
        if (id != SQLObj.getTimeStamp()) {
            Log.e("CalSqlAdapter", "Error inserting row into NthSense! id returned is " + String.valueOf(id));
        }
        return id;
    }

    public String pullTestData(long timestamp) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CalSqlHelper.TABLE_NAME + " WHERE " + CalSqlHelper.UID + "=" + timestamp, null);
        c.moveToFirst();
        return c.getString(1)+"; TAKING DATA: "+Integer.toString(c.getInt(7));
    }


    static class CalSqlHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "cal.db";
        private static final String TABLE_NAME = "NthSense";
        public static final String UID = "_id"; //The UID will actually be the nanosecond
        // Sensor event (should last for 200+ years)


        private static final String ENTRY_X = "xVal";
        private static final String ENTRY_Y = "yVal";
        private static final String ENTRY_Z = "zVal";
        private static final String ENTRY_P = "proximity";
        private static final String ENTRY_LUX = "lux";

        private static final String ENTRY_IS_TRAINING_DATA = "isTrainingData";
        private static final String ENTRY_IS_WALKING = "Walking";

        private static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " (" +
                UID + " INTEGER PRIMARY KEY," +
                ENTRY_X + " REAL, " +
                ENTRY_Y + " REAL, " +
                ENTRY_Z + " REAL, " +
                ENTRY_P + " REAL, " +
                ENTRY_LUX + " REAL, " +
                ENTRY_IS_WALKING + " INTEGER, " +
                ENTRY_IS_TRAINING_DATA + " INTEGER " +
                ");";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;
        private static final int DATABASE_VERSION = 1;

        private Context context;

        public CalSqlHelper(Context context) {
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

                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
}
