package com.shaary.a10000hours.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.shaary.a10000hours.contracts.TrackingActivityView;
import com.shaary.a10000hours.contracts.TimerRepository;
import com.shaary.a10000hours.model.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TrackingActivityPresenter {

    private static final String TAG = TrackingActivityPresenter.class.getSimpleName();
    private TrackingActivityView view;
    private SharedPreferences sharedPreferences;
    private TimerRepository timerRepository;
    private Session timer;

    //Creates an instance of a view through interface so it won't know about real activity
    public TrackingActivityPresenter(TrackingActivityView view, SharedPreferences sharedPreferences, TimerRepository timerRepository, Session timer) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.timerRepository = timerRepository;
        this.timer = timer;
    }

    //Gets time as String from DB then calls display time on activity view
    public void loadTime() {

    }

    //Method for the activity to check if it needs to load data or not
    public long isSharedPrefEmpty() {
        return sharedPreferences.getLong("seconds", 0);
    }

    //When activity is onPause
    public void saveTime() {
        Calendar calendar = Calendar.getInstance();
        timer.setOnPauseTime(calendar.getTimeInMillis());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long onPauseTime = calendar.getTimeInMillis();
        editor.putLong("seconds", timer.getSeconds());
        editor.putLong("onPauseTime", onPauseTime);
        editor.putBoolean("isRunning", timer.isRunning());
        Log.d(TAG, "saveTime: isRunning " + timer.isRunning());
        editor.apply();
    }

    public void retrieveTime() {
        Calendar calendar = Calendar.getInstance();
        long onRelaunchTime = calendar.getTimeInMillis();
        long onPauseTime = sharedPreferences.getLong("onPauseTime", 0);
        boolean isRunning = sharedPreferences.getBoolean("isRunning", false);
        timer.setRunning(isRunning);
        long retrievedSeconds = sharedPreferences.getLong("seconds", 0);
            if (isRunning) {
                Log.d(TAG, "retrieveTime: isRunning!");
                view.updateButtonName("SAVE");
                timer.setSeconds(retrievedSeconds + ((onRelaunchTime - onPauseTime)/1000));
                String time = getTime(timer.getSeconds());
                view.updateTimerView(time);

            }
    }

    //When save button is clicked. Saves to database
    public void saveData() {
        Log.d(TAG, "saveData: data is saved");
        Calendar calendar = Calendar.getInstance();
        long stoppedTime = calendar.getTimeInMillis();
        long startedTime = timer.getStartedTime();
        long timeSpent = stoppedTime - startedTime;
        //started stopped spent
        boolean insertData = timerRepository.add(dateFormatter(startedTime), timeFormatter(stoppedTime), dateFormatterToMins(timeSpent));
        if(insertData){
            //Log.d(TAG, "saveData: insert data == true");
            view.showToast("Data Saved!");
        }else {
            view.showToast("Something went wrong :(");
        }
    }

    public void runTimer() {
        final Handler handler = new Handler();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //boolean isRunning = timer.isRunning();
                    long seconds = timer.getSeconds();
                    String time = getTime(seconds);
                    view.updateTimerView(time);

                    if (timer.isRunning()) {
                        timer.increaseSeconds();
                        Log.d(TAG, "run: is running");
                    }

                    handler.postDelayed(this, 1000);
                }
            });
    }

    private String getTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format(Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, secs);
    }

    public void controlTimer() {
        updateStartTime();
        timer.setRunning(!timer.isRunning());
        if (timer.isRunning()) {
            view.updateButtonName("SAVE");
        } else {
            saveData();
            resetTimer();
        }
    }

    public void resetTimer() {
        view.updateButtonName("START");
        timer.setRunning(false);
        timer.setSeconds(0);
        view.updateTimerView("0:00:00");
    }

    public void updateStartTime() {
        Calendar calendar = Calendar.getInstance();
        timer.setStartedTime(calendar.getTimeInMillis());
    }

    private String dateFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String timeFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String dateFormatterToMins(long time) {
        Log.d(TAG, "dateFormatterToMins: "  + (time / 1000 % 60));
        return "" + time / (60 * 1000) % 60;
    }

}
