package com.shaary.a10000hours.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;
import com.shaary.a10000hours.view_model.SessionsActivityViewModel;

import java.util.List;

// Shows previous records
public class SessionsActivity extends AppCompatActivity {

    private SessionsActivityViewModel viewModel;

    private RecyclerView recyclerView;
    private SessionsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        long skillId = intent.getLongExtra(SkillActivity.EXTRA_ID, 0);

        setContentView(R.layout.activity_database);
        recyclerView = findViewById(R.id.db_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SessionsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(SessionsActivityViewModel.class);

        uiUpdate(skillId);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void uiUpdate(long skillId) {
        viewModel.getSessions(skillId).observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                adapter.submitList(sessions);
            }
        });
    }
}
