package com.shaary.a10000hours.presenter;

import android.os.Handler;
import android.util.Log;

import com.shaary.a10000hours.contracts.TrackingActivityView;
import com.shaary.a10000hours.contracts.TimeRepo;
import com.shaary.a10000hours.model.Timer;

import java.util.Calendar;
import java.util.Locale;

public class TrackingActivityPresenter {

    private static final String TAG = TrackingActivityPresenter.class.getSimpleName();
    private TrackingActivityView view;
    private TimeRepo timeRepo;
    private Timer timer;


    //Creates an instance of a view through interface so it won't know about real activity
    public TrackingActivityPresenter(TrackingActivityView view, TimeRepo timeRepo, Timer timer) {
        this.view = view;
        this.timeRepo = timeRepo;
        this.timer = timer;
    }

    //Gets time as String from DB then calls display time on activity view
    public void loadTime() {
        String time = timeRepo.retrieveTime();
        view.displayTime(time);
    }

    public void showDb() {

    }

    //When activity is on pause
    public void saveTime() {

    }

    public void saveData() {

    }

    public void runTimer() {
        final Handler handler = new Handler();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    boolean isRunning = timer.isRunning();
                    long seconds = timer.getSeconds();
                    long hours = seconds / 3600;
                    long minutes = (seconds % 3600) / 60;
                    long secs = seconds % 60;
                    String time = String.format(Locale.getDefault(),
                            "%d:%02d:%02d", hours, minutes, secs);
                    view.updateTimerView(time);

                    if (isRunning) {
                        timer.increaseSeconds();
                    }

                    handler.postDelayed(this, 1000);
                }
            });
    }

    public void controllTimer() {
        updateStartTime();
        timer.setRunning(!timer.isRunning());
    }


    public void resetTimer() {
        timer.setRunning(false);
        timer.setSeconds(0);
        view.displayTime("0:00:00");

    }

    public void updateStartTime() {
        Calendar calendar = Calendar.getInstance();
        timer.setStartedTime(calendar.getTimeInMillis());
    }
}
