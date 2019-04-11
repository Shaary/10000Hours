package com.shaary.a10000hours.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shaary.a10000hours.model.Repository;
import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;

import java.util.List;

public class TrackingActivityViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Session>> sessions;

    public TrackingActivityViewModel(@NonNull Application application, Skill skill) {
        super(application);
        repository = new Repository(application);
        sessions = repository.getAllSessionsForOneSkill(skill.id);
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
