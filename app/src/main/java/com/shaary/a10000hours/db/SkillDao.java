package com.shaary.a10000hours.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;

import java.util.List;

@Dao
public interface SkillDao {
    @Query("SELECT * FROM Skill")
    LiveData<List<Skill>> getAll();

    @Query("SELECT * FROM skill WHERE id = :id")
    Skill getSkillById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Skill skill);

    @Update
    void update(Skill skill);

    @Query("DELETE FROM skill WHERE id = :id")
    void deleteSkillById(long id);
}
