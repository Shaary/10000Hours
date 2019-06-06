package com.shaary.a10000hours.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.view_model.TimerViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private static final String TAG = TimerFragment.class.getSimpleName();
    public static final String SHAR_PREF = "shared prefs";

    // UI
    private Button startButton;
    private Button cancelButton;
    private TextView timerView;

    private TimerViewModel viewModel;

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

        //viewModel.retrievePrefs();

        // Updates timer
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText = viewModel.timeFormat(aLong);
                timerView.setText(newText);
            }
        };
        if (viewModel.isRunning()) {
            viewModel.resumeTimer();
            subscribe(elapsedTimeObserver);
            startButton.setText("Save");
        }

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
            if (viewModel.isRunning()) {
                unsubscribe(elapsedTimeObserver);
                startButton.setText("Start");
                viewModel.stopTimer();
            }
        });

        return view;
    }

    private void save(long skillId, String time) {
        viewModel.saveTime(time, skillId);
    }

    private void subscribe(Observer<Long> observer) {
        viewModel.getElapsedTime().observe(this, observer);
    }
    private void unsubscribe(Observer<Long> observer) {
        viewModel.getElapsedTime().removeObserver(observer);
        timerView.setText("00:00:00");
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.sharedPrefSave();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        //viewModel.retrievePrefs();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
