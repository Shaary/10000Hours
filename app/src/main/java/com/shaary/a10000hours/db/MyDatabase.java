package com.shaary.a10000hours.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shaary.a10000hours.model.Session;

@Database(entities = {Session.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();

    private static volatile MyDatabase INSTANCE;

    static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "my_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
