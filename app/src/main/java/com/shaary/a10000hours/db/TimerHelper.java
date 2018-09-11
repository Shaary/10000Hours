package com.shaary.a10000hours.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimerHelper extends SQLiteOpenHelper {

    public static final String TABLE_DATE_TIME = "DATE_TIME_DATA";
    public static final String COLUMN_DATE = "DATE_DATA";
    public static final String ID = "_ID";

    private static final String DB_NAME = "date_time.db";
    private static final int DB_VERSION = 1;

    public static final String DB_CREATE =
            "CREATE TABLE " + TABLE_DATE_TIME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + "  DATE)";

    public TimerHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
