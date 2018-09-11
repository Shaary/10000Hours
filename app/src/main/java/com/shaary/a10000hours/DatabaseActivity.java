package com.shaary.a10000hours;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shaary.a10000hours.db.TimerDataSource;
import com.shaary.a10000hours.db.TimerHelper;

public class DatabaseActivity extends AppCompatActivity {
    private TimerDataSource dataSource;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        dataSource = new TimerDataSource(this);
        dataSource.read();
        database = dataSource.getDatabase();

        Cursor cursor = database.query(TimerHelper.TABLE_DATE_TIME, new String[] {TimerHelper.ID, TimerHelper.COLUMN_DATE},
                null, null, null, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}
