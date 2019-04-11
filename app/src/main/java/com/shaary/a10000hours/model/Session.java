package com.shaary.a10000hours.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Skill.class,
        parentColumns = "id",
        childColumns = "skillId",
        onDelete = ForeignKey.CASCADE
))
public class Session {
    @PrimaryKey (autoGenerate = true)
    public long timeId;
    @ColumnInfo(index = true)
    private long skillId;
    //Timer vars
    private boolean isRunning = false;
    private long seconds = 0;
    private long startedTime = 0;
    //TODO: decide if I need stoppedTime in here or can keep it as Presenters var
    private long stoppedTime = 0;
    private long onPauseTime = 0;
    private long onRetrieveTime = 0;

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(long startedTime) {
        this.startedTime = startedTime;
    }

    public long getStoppedTime() {
        return stoppedTime;
    }

    public void setStoppedTime(long stoppedTime) {
        this.stoppedTime = stoppedTime;
    }

    public long getOnPauseTime() {
        return onPauseTime;
    }

    public void setOnPauseTime(long onPauseTime) {
        this.onPauseTime = onPauseTime;
    }

    public long getOnRetrieveTime() {
        return onRetrieveTime;
    }

    public void setOnRetrieveTime(long onRetrieveTime) {
        this.onRetrieveTime = onRetrieveTime;
    }

    public void increaseSeconds() {
        seconds++;
    }
}


