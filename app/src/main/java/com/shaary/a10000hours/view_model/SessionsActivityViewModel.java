package com.shaary.a10000hours.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shaary.a10000hours.model.Repository;
import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;

import java.util.List;

public class SessionsActivityViewModel extends AndroidViewModel {

    private Repository repository;
    // Might not need it here
    private LiveData<List<Session>> sessions;

    public SessionsActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Session>> getSessions(long skillId) {
        sessions = repository.getAllSessionsForOneSkill(skillId);
        return sessions;
    }
}
