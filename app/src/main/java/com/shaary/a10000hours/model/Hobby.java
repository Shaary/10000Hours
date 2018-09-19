package com.shaary.a10000hours.model;

import android.media.Image;

public class Hobby {

    private String name;
    private Image image;
    private String time;

    public Hobby(String name) {
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
