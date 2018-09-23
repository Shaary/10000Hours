package com.shaary.a10000hours.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shaary.a10000hours.contracts.TimerRepository;

public class TimerDatabaseHelper extends SQLiteOpenHelper implements TimerRepository {
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

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    //TODO: find another way to reference to time in the array
    @Override
    public boolean add(String... items) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String startTime = items[0];
        String stopTime = items[1];
        String timeSpent = items[2];

        contentValues.put(COL2, startTime);
        contentValues.put(COL3, stopTime);
        contentValues.put(COL4, timeSpent);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    @Override
    public void update(long item) {

    }

    @Override
    public void remove(long item) {

    }
}
