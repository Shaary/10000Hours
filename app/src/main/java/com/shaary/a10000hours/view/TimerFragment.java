package com.shaary.a10000hours.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.view_model.TimerViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private static final String TAG = TimerFragment.class.getSimpleName();

    // UI
    private Button startButton;
    private Button cancelButton;
    private TextView timerView;

    private TimerViewModel viewModel;

    private SharedPreferences sharedPreferences;
    private boolean isRunning = false;
    private long seconds = 0;
    private long startedTime;
    private long skillId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        startButton = view.findViewById(R.id.start_button);
        cancelButton = view.findViewById(R.id.reset_button);
        timerView = view.findViewById(R.id.timer_view);

        viewModel = ViewModelProviders.of(this).get(TimerViewModel.class);

        skillId = this.getArguments().getLong("skillId", 0);

        // Updates timer
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText = viewModel.timeFormat(aLong);
                timerView.setText(newText);
            }
        };

        startButton.setOnClickListener(v -> {
            if (startButton.getText().equals("Start")) {
                viewModel.startTimer();
                subscribe(elapsedTimeObserver);
                startButton.setText("Save");
            } else {
                viewModel.stopTimer();
                //Saves time to DB
                save(skillId, timerView.getText().toString());

                unsubscribe(elapsedTimeObserver);
                startButton.setText("Start");
            }

        });

        cancelButton.setOnClickListener(v -> {
            unsubscribe(elapsedTimeObserver);
            startButton.setText("Start");
        });

        return view;
    }

    private void save(long skillId, String time) {
        viewModel.saveTime(time, skillId);
    }

    //Method for the activity to check if it needs to load data or not
    public long isSharedPrefEmpty() {
        return sharedPreferences.getLong("seconds", 0);
    }

    //When activity is onPause
    public void saveTime() {
        Calendar calendar = Calendar.getInstance();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long onPauseTime = calendar.getTimeInMillis();
        editor.putLong("seconds", seconds);
        editor.putLong("onPauseTime", onPauseTime);
        editor.putLong("startTime", startedTime);
        editor.putBoolean("isRunning", isRunning);
        editor.apply();
    }

    public void retrieveTime() {
        Calendar calendar = Calendar.getInstance();
        long onRelaunchTime = calendar.getTimeInMillis();
        long onPauseTime = sharedPreferences.getLong("onPauseTime", 0);
        isRunning = sharedPreferences.getBoolean("isRunning", false);
        long retrievedSeconds = sharedPreferences.getLong("seconds", 0);
        if (isRunning) {
            Log.d(TAG, "retrieveTime: isRunning!");
            //view.updateButtonName("SAVE");
            seconds = (retrievedSeconds + ((onRelaunchTime - onPauseTime)/1000));
            String time = getTime(seconds);
            //view.updateTimerView(time);

        }
    }

    //When save button is clicked. Saves to database
    public void saveData() {
        Log.d(TAG, "saveData: data is saved");
        Calendar calendar = Calendar.getInstance();
        long stoppedTime = calendar.getTimeInMillis();
        long timeSpent = stoppedTime - startedTime;
        //started stopped spent
    }

    private String getTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format(Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, secs);
    }

    private void subscribe(Observer<Long> observer) {
        viewModel.getElapsedTime().observe(this, observer);
    }
    private void unsubscribe(Observer<Long> observer) {
        viewModel.getElapsedTime().removeObserver(observer);
        timerView.setText("00:00:00");
    }
}
