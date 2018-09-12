package com.shaary.a10000hours;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shaary.a10000hours.db.TimerDatabaseHelper;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    private TimerDatabaseHelper db;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        listView = findViewById(R.id.list_view);

        db = new TimerDatabaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor cursor = db.getListContents();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                theList.add(cursor.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    //TODO: format the time
}
