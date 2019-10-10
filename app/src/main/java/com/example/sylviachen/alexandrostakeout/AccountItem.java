package com.example.sylviachen.alexandrostakeout;

import android.graphics.Bitmap;

public class AccountItem {
    private String name;
    private Bitmap bitmap;

    public AccountItem(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
