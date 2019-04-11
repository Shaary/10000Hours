package com.shaary.a10000hours.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shaary.a10000hours.R;
import com.shaary.a10000hours.contracts.MainActivityView;
import com.shaary.a10000hours.presenter.MainActivityViewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CreateHobbyFragment.OnOkButtonListener, MainActivityView{

    MainActivityViewPresenter presenter;

    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.floatingActionButton) FloatingActionButton fab;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityViewPresenter(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }

    public void fabClick(View view) {
//        Intent intent = new Intent(this, TrackingActivity.class);
//        startActivity(intent);

        CreateHobbyFragment createHobbyFragment = new CreateHobbyFragment();
        createHobbyFragment.show(getSupportFragmentManager(), "create_hobby");
    }

    @Override
    public void okClicked(String name) {
        presenter.addToList(name);
        adapter.notifyDataSetChanged();
    }
}
