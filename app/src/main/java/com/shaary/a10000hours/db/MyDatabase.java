package com.shaary.a10000hours.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.shaary.a10000hours.model.Session;
import com.shaary.a10000hours.model.Skill;

@Database(entities = {Session.class, Skill.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();
    public abstract SkillDao skillDao();

    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "my_database")
                            //Change this later to separate threads
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
