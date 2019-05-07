package com.shaary.a10000hours.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shaary.a10000hours.model.Repository;
import com.shaary.a10000hours.model.Skill;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Skill>> skills;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        skills = repository.getAllSkills();
    }

    public void insert(Skill skill){
        repository.insertSkill(skill);
    }

    // Might not need them in the main activity
    public void update(Skill skill) {
        repository.updateSkill(skill);
    }
//
//    public void delete(Skill skill) {
//        repository.deleteSkill(skill.id);
//    }

    public LiveData<List<Skill>> getAllSkills() {
        return skills;
    }

}
