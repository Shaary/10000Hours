package com.shaary.a10000hours.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.db.TimerDatabaseHelper;

import java.util.ArrayList;

public class SessionsActivity extends AppCompatActivity {

    private TimerDatabaseHelper db;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_database);
        listView = findViewById(R.id.list_view);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    //TODO: format the time
}
