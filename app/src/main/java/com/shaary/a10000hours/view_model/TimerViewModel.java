package com.shaary.a10000hours.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shaary.a10000hours.model.Repository;
import com.shaary.a10000hours.model.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimerViewModel extends AndroidViewModel {
    private static final int ONE_SECOND = 1000;
    private static final String TIME = "TIME";
    private static final String TAG = TimerViewModel.class.getSimpleName();

    private Repository repository;
    private SharedPreferences sharedPreferences;

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    private long mInitialTime = 0;
    private boolean isRunning;
    private Timer timer;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        isRunning = sharedPreferences.getBoolean("is running", false);
        Log.d(TAG, "TimerViewModel: start is running " + isRunning);
    }

    public void startTimer() {
        isRunning = true;

        mInitialTime = SystemClock.elapsedRealtime();

        timer = new Timer();

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / ONE_SECOND;
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public void resumeTimer() {
        timer = new Timer();
        mInitialTime = sharedPreferences.getLong("initial time", 0);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / ONE_SECOND;
                // setValue() cannot be called from a background thread so post to main thread.
                mElapsedTime.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public void stopTimer() {
        timer.cancel();
        timer = null;
        isRunning = false;
        mInitialTime = 0;
    }

    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }


    // Saves to DB
    public void saveTime(String time, long skillId) {
        Session session = new Session(skillId, time, new Date());
        repository.insertSession(session);
    }

    public String timeFormat(long totalSecs) {
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String dateFormatter(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public void sharedPrefSave() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        Log.d(TAG, "sharedPrefSave: is running " + isRunning);
        editor.putBoolean("is running", isRunning);
        if (isRunning) {
            editor.putLong("initial time", mInitialTime);
        }
        Log.d(TAG, "sharedPrefSave: is running " + isRunning);
        editor.apply();
    }

    public void retrievePrefs() {
        mInitialTime = sharedPreferences.getLong("initial time", 0);
        isRunning = sharedPreferences.getBoolean("is running", false);
        Log.d(TAG, "retrievePrefs: is running " + isRunning);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public long getmInitialTime() {
        return mInitialTime;
    }

}
