package com.shaary.a10000hours.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

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
    private String sessionTime = "00:00:00";
    private Date sessionDate;

    public Session(long skillId, String sessionTime, Date sessionDate) {
        this.skillId = skillId;
        this.sessionTime = sessionTime;
        this.sessionDate = sessionDate;
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public long getTimeId() {
        return timeId;
    }

    public void setTimeId(long timeId) {
        this.timeId = timeId;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }
}


