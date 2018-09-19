package com.shaary.a10000hours.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.db.TimerDatabaseHelper;
import com.shaary.a10000hours.model.Timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackingActivity extends AppCompatActivity {

    public static final String TAG  = TrackingActivity.class.getSimpleName();

    private Timer timer;

    //DB var
    private TimerDatabaseHelper db;

    //Timer vars
    private boolean isRunning = false;
    private long seconds = 0;
    private long startedTime = 0;
    private long stoppedTime = 0;
    private long onPauseTime = 0;

    //UI vars
    @BindView(R.id.start_button) Button startButton;
    @BindView(R.id.reset_button) Button resetButton;
    @BindView(R.id.timer_view) TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);

        timer = new Timer(this);

        db = new TimerDatabaseHelper(this);

        SharedPreferences sharedPref = getSharedPreferences("time", MODE_PRIVATE);
        long retrievedSeconds = sharedPref.getLong("seconds", 0);

        //Check if saved time is not empty
        if (retrievedSeconds != 0) {
            Calendar calendar = Calendar.getInstance();

            //Get time now and add time since the timer was started
            long onRelaunchTime = calendar.getTimeInMillis();
            onPauseTime = sharedPref.getLong("onPauseTime", 0);
            isRunning = sharedPref.getBoolean("isRunning", false);
            if (isRunning) {
                startButton.setText(R.string.save_button);
                seconds = retrievedSeconds + ((onRelaunchTime - onPauseTime)/1000);
            } else {
                seconds = retrievedSeconds;
            }
        }
        runTimer();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: has bee called");
        super.onPause();
        saveTime();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: has been called");
        super.onDestroy();
        saveTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //retrieveTime();
        Calendar calendar = Calendar.getInstance();
        long onResumeTime = calendar.getTimeInMillis();
    }

    public void runTimer() {
        Calendar calendar = Calendar.getInstance();
        startedTime = calendar.getTimeInMillis();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long hours = seconds/3600;
                long minutes = (seconds%3600)/60;
                long secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void saveTime() {
        //Saving time to keep track of the timer when the app's stopped
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar calendar = Calendar.getInstance();
        onPauseTime = calendar.getTimeInMillis();
        editor.putLong("seconds", seconds);
        editor.putLong("onPauseTime", onPauseTime);
        editor.putBoolean("isRunning", isRunning);
        editor.apply();
    }

    private void retrieveTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        onPauseTime = sharedPreferences.getLong("onPauseTime", 0);
    }

    //Button clicks methods start

    public void startTimer(View view) {
        //Checks if the timer is running and performs according actions
        if (!isRunning) {
            isRunning = true;
            Calendar calendar = Calendar.getInstance();
            startedTime = calendar.getTimeInMillis();
            startButton.setText(R.string.save_button);
        } else {
            isRunning = false;
            saveData();
            seconds = 0;
            startButton.setText(R.string.start_button_text);
        }
    }

    public void resetTimer(View view) {
        isRunning = false;
        seconds = 0;
        startButton.setText(R.string.start_button_text);
    }

    public void showDB(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }
    //Button clicks end


    public void saveData() {
        Calendar calendar = Calendar.getInstance();
        stoppedTime = calendar.getTimeInMillis();
        long timeSpent = stoppedTime - startedTime;
        AddData(startedTime, stoppedTime, timeSpent);
    }


    //Adds time to database
    private void AddData(long startedTime, long stoppedTime, long timeSpent) {
        boolean insertData = db.addData(startedTime, stoppedTime, timeSpent);

        if(insertData){
            Toast.makeText(this, "Data Saved!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_LONG).show();
        }
    }

    private String dateFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }
}
