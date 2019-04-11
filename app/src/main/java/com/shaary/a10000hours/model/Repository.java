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

    //TODO: move methods below onto separate thread
    // For SessionDao
    public void insertSession(Session session) {
        sessionDao.insert(session);
    }

    public void deleteSession(long sessionId) {
        sessionDao.deleteSessionById(sessionId);
    }

    public void updateSession(Session session) {
        sessionDao.update(session);
    }

    public LiveData<List<Session>> getAllSessionsForOneSkill(long skillId) {
        return sessionDao.getAllForOneSkill(skillId);
    }

    // For SkillDao
    public void insertSkill(Skill skill) {
        skillDao.insert(skill);
    }

    public void deleteSkill(long skillId) {
        skillDao.deleteSkillById(skillId);
    }

    public void updateSkill(Skill skill) {
        skillDao.update(skill);
    }

    public LiveData<List<Skill>> getAllSkills() {
        return skills;
    }
}
