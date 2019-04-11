package com.shaary.a10000hours.presenter;

import com.shaary.a10000hours.contracts.MainActivityView;
import com.shaary.a10000hours.contracts.MyViewHolderView;
import com.shaary.a10000hours.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewPresenter {

    private static final String TAG = MainActivityViewPresenter.class.getSimpleName();
    private final MainActivityView mainView;

    //TODO: figure out how to save hobbies list so that it'll be shown even after closing the app
    private List<Skill> hobbies = new ArrayList<>();

    public MainActivityViewPresenter(MainActivityView mainView) {

        this.mainView = mainView;
    }

    public int getHobbiesListSize() {
        return hobbies.size();
    }

    public void addToList(String name) {
        //Log.d(TAG, "addToList: is called");
        Skill hobby = new Skill(name);
        hobbies.add(hobby);
    }

    public void setUpRecyclerView(int position, MyViewHolderView view) {
        //Log.d(TAG, "setUpRecyclerView: is called");
        Skill hobby = hobbies.get(position);
        //Log.d(TAG, "setUpRecyclerView: name " + hobby.getName());
        view.setName(hobby.getName());
    }
}
