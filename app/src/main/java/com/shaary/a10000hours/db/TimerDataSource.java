package com.shaary.a10000hours.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.tv.TvInputManager;

import java.util.Date;

public class TimerDataSource {

    private SQLiteDatabase database;
    private TimerHelper timerHelper;
    private Context context;

    public TimerDataSource(Context context) {
        this.context = context;
        timerHelper = new TimerHelper(this.context);
    }

    // open
    public void open() throws SQLiteException {
        database = timerHelper.getWritableDatabase();
    }

    // close
    public void close() {
        database.close();
    }

    // insert
    public void insertTimer(Date date) {
        database.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            // modify the method to take a string
            contentValues.put(TimerHelper.COLUMN_DATE, 0);
            database.insert(TimerHelper.TABLE_DATE_TIME, null, contentValues);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

    }

    // select

    // update

    // delete
}
