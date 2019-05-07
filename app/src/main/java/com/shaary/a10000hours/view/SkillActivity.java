package com.shaary.a10000hours.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.view_model.TrackingActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

// Shows skill 'main' screen
public class SkillActivity extends AppCompatActivity {

    public static final String TAG  = SkillActivity.class.getSimpleName();

    public static final String EXTRA_ID = "com.shaary.a10000hours.EXTRA_ID";
    public static final String EXTRA_LVL = "com.shaary.a10000hours.EXTRA_LVL";
    public static final String EXTRA_NAME = "com.shaary.a10000hours.EXTRA_NAME";
    public static final String EXTRA_TIME = "com.shaary.a10000hours.EXTRA_TIME";

    private TrackingActivityViewModel viewModel;

    //UI vars
    @BindView(R.id.time_text) TextView timeView;
    @BindView(R.id.skill_name_text) TextView skillName;
    @BindView(R.id.level_text) TextView levelText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(TrackingActivityViewModel.class);

        Intent intent = getIntent();
        Log.d(TAG, "Skill name " + intent.getStringExtra(EXTRA_NAME));
        timeView.setText(intent.getStringExtra(EXTRA_TIME));
        skillName.setText(intent.getStringExtra(EXTRA_NAME));
        levelText.setText("Level " + intent.getIntExtra(EXTRA_LVL, 1));



    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: has bee called");
        super.onPause();
        //presenter.saveTime();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: has been called");
        super.onDestroy();
        //presenter.saveTime();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: has been called");
        super.onResume();
    }

    //Button clicks methods --start--
    public void startTimer(View view) {
        //Checks if the timer is running and performs according actions
        //presenter.controlTimer();
    }

//    public void resetTimer(View view) {
//        presenter.resetTimer();
//    }

    public void showDB(View view) {
        Intent intent = new Intent(this, SessionsActivity.class);
        startActivity(intent);
    }
    //Button clicks --end--
}