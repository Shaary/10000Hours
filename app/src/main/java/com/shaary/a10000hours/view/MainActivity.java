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
import android.widget.Toast;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.model.Skill;
import com.shaary.a10000hours.view_model.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CreateHobbyFragment.OnOkButtonListener {

    public static final int ADD_SKILL_REQUEST = 1;
    public static final int EDIT_SKILL_REQUEST = 2;

    private MainViewModel mainViewModel;

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

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        uiUpdate();
        adapter.setOnItemClickListener(skill -> {
            Intent intent = new Intent(this, SkillActivity.class);
            intent.putExtra(SkillActivity.EXTRA_ID, skill.id);
            intent.putExtra(SkillActivity.EXTRA_NAME, skill.getName());
            intent.putExtra(SkillActivity.EXTRA_TIME, skill.getTime());
            intent.putExtra(SkillActivity.EXTRA_LVL, skill.getLvl());
            startActivityForResult(intent, EDIT_SKILL_REQUEST);
        });
    }

    public void fabClick(View view) {
        CreateHobbyFragment createHobbyFragment = new CreateHobbyFragment();
        createHobbyFragment.show(getSupportFragmentManager(), "create_hobby");
    }

    private void uiUpdate() {
        mainViewModel.getAllSkills().observe(this, new Observer<List<Skill>>() {
            @Override
            public void onChanged(@Nullable List<Skill> skills) {
                adapter.submitList(skills);
            }
        });
    }

    @Override
    public void okClicked(String name) {
        Skill skill = new Skill(name);
        mainViewModel.insert(skill);
        Log.d(TAG, " new skill " + skill.getName());
        Log.d(TAG, "okClicked: name " + name);
        uiUpdate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_SKILL_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(SkillActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Skill can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(SkillActivity.EXTRA_NAME);
            String time = data.getStringExtra(SkillActivity.EXTRA_TIME);
            int level = data.getIntExtra(SkillActivity.EXTRA_LVL, 1);
            //Date date = (Date) data.getSerializableExtra(AddNoteActivity.EXTRA_DATE);

            Skill skill = new Skill(name, time, level);
            skill.id = id;
            mainViewModel.update(skill);
            Toast.makeText(this, "Skill updated", Toast.LENGTH_SHORT).show();
        }
    }
}
