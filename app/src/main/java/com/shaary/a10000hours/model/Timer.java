package com.shaary.a10000hours.model;

import android.content.Context;
import android.os.Handler;

import com.shaary.a10000hours.db.TimerDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Timer {

    private Context context;

    private TimerDatabaseHelper db;

    //Timer vars
    private boolean isRunning = false;
    private long seconds = 0;
    private long startedTime = 0;
    private long stoppedTime = 0;
    private long onPauseTime = 0;

//    public Timer(Context context) {
//        this.context = context;
//    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(long startedTime) {
        this.startedTime = startedTime;
    }

    public long getStoppedTime() {
        return stoppedTime;
    }

    public void setStoppedTime(long stoppedTime) {
        this.stoppedTime = stoppedTime;
    }

    public long getOnPauseTime() {
        return onPauseTime;
    }

    public void setOnPauseTime(long onPauseTime) {
        this.onPauseTime = onPauseTime;
    }

    public void createDb() {
        db = new TimerDatabaseHelper(context);
    }

    public String runTimer() {
        Calendar calendar = Calendar.getInstance();
        startedTime = calendar.getTimeInMillis();
        final Handler handler = new Handler();
        final String[] time = new String[1];
        handler.post(new Runnable() {
            @Override
            public void run() {
                long hours = seconds/3600;
                long minutes = (seconds%3600)/60;
                long secs = seconds%60;
                time[0] = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
        return time[0];
    }

    public String timer() {
        long hours = seconds/3600;
        long minutes = (seconds%3600)/60;
        long secs = seconds%60;
        String time = String.format(Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, secs);
        if (isRunning) {
            seconds++;
        }

        return time;
    }


    private String dateFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public void increaseSeconds() {
        seconds++;
    }
}
