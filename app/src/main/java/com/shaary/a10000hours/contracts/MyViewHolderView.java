package com.shaary.a10000hours.contracts;

import android.graphics.drawable.Drawable;

public interface MyViewHolderView {
    void setName(String name);
    void setTime(String time);
    void setImage(Drawable image);
    void refreshHobbiesList();
}
