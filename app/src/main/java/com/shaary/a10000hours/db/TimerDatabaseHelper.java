package com.shaary.a10000hours.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "timer.db";
    public static final String TABLE_NAME = "timer_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "STARTED_TIME";
    public static final String COL3 = "STOPPED_TIME";
    public static final String COL4 = "TIME_SPENT";
    private static final String TAG = TimerDatabaseHelper.class.getSimpleName();

    public TimerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + "  DATE," + COL3 + "  DATE," + COL4 + "  TIME)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(long startedTime, long stoppedTime, long timeSpent) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String formattedStartTime = dateFormatter(startedTime);
        String formattedStopTime = timeFormatter(stoppedTime);
        String formattedTimeSpent = dateFormatterToMins(timeSpent);

        contentValues.put(COL2, formattedStartTime);
        contentValues.put(COL3, formattedStopTime);
        contentValues.put(COL4, formattedTimeSpent);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    private String dateFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String timeFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String dateFormatterToMins(long time) {
        Log.d(TAG, "dateFormatterToMins: "  + (time / 1000 % 60));
        return "" + time / (60 * 1000) % 60;
    }
}
