package com.shaary.a10000hours.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.shaary.a10000hours.R;

// Shows previous records
public class SessionsActivity extends AppCompatActivity {

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
}
