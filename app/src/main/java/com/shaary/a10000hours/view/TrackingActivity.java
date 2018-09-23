package com.shaary.a10000hours.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.contracts.TrackingActivityView;
import com.shaary.a10000hours.presenter.TrackingActivityPresenter;
import com.shaary.a10000hours.db.TimerDatabaseHelper;
import com.shaary.a10000hours.model.Timer;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackingActivity extends AppCompatActivity implements TrackingActivityView{

    public static final String TAG  = TrackingActivity.class.getSimpleName();

    TrackingActivityPresenter presenter;

    //DB var
    private TimerDatabaseHelper db;

    //UI vars
    @BindView(R.id.start_button) Button startButton;
    @BindView(R.id.reset_button) Button resetButton;
    @BindView(R.id.timer_view) TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);

        Timer timer = new Timer();
        db = new TimerDatabaseHelper(this);
        SharedPreferences sharedPref = getSharedPreferences("time", MODE_PRIVATE);
        presenter = new TrackingActivityPresenter(this, sharedPref, db, timer);

        //Check if saved time is not empty
        if (presenter.isSharedPrefEmpty() != 0) {
            presenter.retrieveTime();
        }

        presenter.runTimer();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: has bee called");
        super.onPause();
        presenter.saveTime();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: has been called");
        super.onDestroy();
        presenter.saveTime();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: has been called");
        super.onResume();
    }

    //Button clicks methods --start--
    public void startTimer(View view) {
        //Checks if the timer is running and performs according actions
        presenter.controlTimer();
    }

    public void resetTimer(View view) {
        presenter.resetTimer();
    }

    public void showDB(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }
    //Button clicks --end--


    //The interface methods start
    @Override
    public void updateButtonName(String name) {
        startButton.setText(name);
    }

    //TODO: create a timer method that will stop on double click start button
    //TODO: hint: to stop timer use  handler.removeCallbacks(Runnable) to save resources
    @Override
    public void updateTimerView(String time) {
        timeView.setText(time);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
