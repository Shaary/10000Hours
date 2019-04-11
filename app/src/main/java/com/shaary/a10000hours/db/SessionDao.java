package com.shaary.a10000hours.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shaary.a10000hours.model.Session;

import java.util.List;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM Session")
    List<Session> getAll();

    @Query("SELECT * FROM Session WHERE timeId = :id")
    Session getTimeById(long id);

    @Insert
    long insert(Session session);

    @Update
    void update(Session session);

    @Query("DELETE FROM session WHERE timeId :=id")
    void deleteTimeById(long id);
}
