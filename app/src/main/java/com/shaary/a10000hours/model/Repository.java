package com.shaary.a10000hours.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.shaary.a10000hours.db.MyDatabase;
import com.shaary.a10000hours.db.SessionDao;
import com.shaary.a10000hours.db.SkillDao;

import java.util.List;

public class Repository {
    private SkillDao skillDao;
    private SessionDao sessionDao;
    private LiveData<List<Skill>> skills;

    public Repository(Application application) {
        MyDatabase database = MyDatabase.getDatabase(application);
        skillDao = database.skillDao();
        sessionDao = database.sessionDao();
        skills = skillDao.getAll();
    }

    public void insert(Skill skill) {
        skillDao.insert(skill);
    }

    public void delete(long skillId) {
        skillDao.deleteSkillById(skillId);
    }

    public void update(Skill skill) {
        skillDao.update(skill);
    }

    public LiveData<List<Skill>> getAllSkills() {
        return skills;
    }
}
