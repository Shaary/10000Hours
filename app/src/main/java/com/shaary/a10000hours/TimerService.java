package com.shaary.a10000hours;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TimerService extends Service {

    private static final String TAG = TimerService.class.getSimpleName();
    private Timer timer;
    private IBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isRunning = intent.getBooleanExtra("isRunning", false);
        long seconds = intent.getLongExtra("seconds", 0);
        timer.setRunning(isRunning);
        timer.setSeconds(seconds);
        timer.runTimer();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        timer.setRunning(false);
    }

    public class LocalBinder extends Binder {

        public TimerService getTimerService() {
            return TimerService.this;
        }
    }

    //Client methods

    public void startTimer() {
        timer.runTimer();
    }

    public void pauseTimer() {
        timer.setRunning(false);
    }

    public Timer getTimer() {
        return timer;
    }
}
