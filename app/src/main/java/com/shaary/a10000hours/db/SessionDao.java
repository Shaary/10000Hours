package com.shaary.a10000hours.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shaary.a10000hours.model.Session;

import java.util.List;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM Session WHERE skillId = :skillId")
    LiveData<List<Session>> getAllForOneSkill(long skillId);

    @Query("SELECT * FROM Session WHERE timeId = :id")
    Session getSessionById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Session session);

    @Update
    void update(Session session);

    @Query("DELETE FROM session WHERE timeId = :id")
    void deleteSessionById(long id);
}
