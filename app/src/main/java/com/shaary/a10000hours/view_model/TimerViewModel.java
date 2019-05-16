package com.shaary.a10000hours.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.shaary.a10000hours.model.Repository;
import com.shaary.a10000hours.model.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimerViewModel extends AndroidViewModel {
    private static final int ONE_SECOND = 1000;

    private Repository repository;

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    private long mInitialTime;
    private Timer timer;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void startTimer() {
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

    public void stopTimer() {
        timer.cancel();
        timer = null;
    }

    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }

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
}
