package com.shaary.a10000hours;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean isRunning = false;
    private long seconds = 0;
    private long startedTime = 0;
    private long stoppedTime = 0;
    private long onPauseTime = 0;
    private long onResumeTime = 0;
    private long onRelaunchTime = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button startButton;
    private Button resetButton;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        resetButton = findViewById(R.id.reset_button);
        timeView = findViewById(R.id.timer_view);

        SharedPreferences sharedPref = getSharedPreferences("time", MODE_PRIVATE);
        long retrievedSeconds = sharedPref.getLong("seconds", 0);
        if (retrievedSeconds != 0) {
            Calendar calendar = Calendar.getInstance();
            onRelaunchTime = calendar.getTimeInMillis();
            onPauseTime = sharedPref.getLong("onPauseTime", 0);
            isRunning = sharedPref.getBoolean("isRunning", false);
            if (isRunning) {
                startButton.setText(R.string.pause_button_text);
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
        onResumeTime = calendar.getTimeInMillis();
//        long diffTime = onResumeTime - onPauseTime;
//        Date date = new Date(diffTime);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
//        String formattedDate = dateFormat.format(date);

    }

    private void runTimer() {
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


    public void onStartClick(View view) {
        if (!isRunning) {
            isRunning = true;
            startButton.setText(R.string.pause_button_text);
        } else {
            isRunning = false;
            startButton.setText(R.string.start_button_text);
        }

    }

    public void onResetClick(View view) {
        isRunning = false;
        seconds = 0;
        startButton.setText(R.string.start_button_text);
    }

    private void saveTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar calendar = Calendar.getInstance();
        onPauseTime = calendar.getTimeInMillis();
//        Date date = new Date(onPauseTime);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss", Locale.getDefault());
//        String formattedDate = dateFormat.format(date);
        editor.putLong("seconds", seconds);
        editor.putLong("onPauseTime", onPauseTime);
        editor.putBoolean("isRunning", isRunning);
        editor.apply();
    }

    private void retrieveTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        onPauseTime = sharedPreferences.getLong("onPauseTime", 0);
    }

}
