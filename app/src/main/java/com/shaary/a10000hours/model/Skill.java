package com.shaary.a10000hours.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;

import java.util.List;

@Entity
public class Skill {

    @PrimaryKey(autoGenerate = true) public long id;
    private String name;
    private String time = "0";
    private int lvl = 1;

    public Skill(String name) {
        this.name = name;
    }

    @Ignore
    public Skill(String name, String time, int lvl) {
        this.name = name;
        this.time = time;
        this.lvl = lvl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
}
