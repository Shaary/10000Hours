package com.shaary.a10000hours.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.contracts.MainActivityView;
import com.shaary.a10000hours.model.Skill;
import com.shaary.a10000hours.view_model.SkillViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CreateHobbyFragment.OnOkButtonListener, SkillAdapter.onHobbyClickListener{

    private SkillViewModel skillViewModel;

    private SkillAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.floatingActionButton) FloatingActionButton fab;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SkillAdapter();
        recyclerView.setAdapter(adapter);

        skillViewModel = ViewModelProviders.of(this).get(SkillViewModel.class);
        uiUpdate();
        adapter.setOnItemClickListener(skill -> {
            Intent intent = new Intent(this, TrackingActivity.class);
            intent.putExtra("id", skill.id);
            startActivity(intent);
        });
    }

    public void fabClick(View view) {
        CreateHobbyFragment createHobbyFragment = new CreateHobbyFragment();
        createHobbyFragment.show(getSupportFragmentManager(), "create_hobby");
    }

    private void uiUpdate() {
        skillViewModel.getAllSkills().observe(this, new Observer<List<Skill>>() {
            @Override
            public void onChanged(@Nullable List<Skill> skills) {
                adapter.submitList(skills);
            }
        });
    }

    @Override
    public void okClicked(String name) {
        Skill skill = new Skill(name);
        skillViewModel.insert(skill);
        Log.d(TAG, "okClicked: name " + name);
        uiUpdate();
    }

    @Override
    public void skillClicked(Skill skill) {
        Log.d(TAG, "skillClicked: clicked");
        Intent intent = new Intent(this, TrackingActivity.class);
        intent.putExtra("id", skill.id);
        startActivity(intent);
    }
}
