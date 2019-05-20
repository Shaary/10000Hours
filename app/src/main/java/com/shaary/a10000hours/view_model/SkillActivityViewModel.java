package com.shaary.a10000hours.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.shaary.a10000hours.model.Repository;
import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;

import java.util.List;

public class SkillActivityViewModel extends AndroidViewModel {
    private Repository repository;
    // Might not need it here
    private LiveData<List<Session>> sessions;

    public SkillActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    // To edit and update skills
    public void update(Skill skill) {
        repository.updateSkill(skill);
    }
    public void delete(Skill skill) {
        repository.deleteSkill(skill.id);
    }

    public void insert(Session session) {
        repository.insertSession(session);
    }

    public void update(Session session) {
        repository.updateSession(session);
    }

    public void delete(Session session) {
        repository.deleteSession(session.timeId);
    }

    public LiveData<List<Session>> getAllTime() {
        return sessions;
    }
}
