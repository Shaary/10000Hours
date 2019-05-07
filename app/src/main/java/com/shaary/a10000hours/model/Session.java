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
    private long sessionTime = 0;
    private Date sessionDate;


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

    public long getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(long sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }
}


