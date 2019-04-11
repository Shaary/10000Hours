package com.shaary.a10000hours.model;

import android.media.Image;

import java.util.List;

public class Skill {

    private String name;
    private Image image;
    private String time;
    private List<Skill> hobbies;

    public List<Skill> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Skill> hobbies) {
        this.hobbies = hobbies;
    }

    public Skill(String name) {
        this.name = name;
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

    private class ImagesHolder {

    }
}
