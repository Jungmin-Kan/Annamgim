package com.worldofuiux.fashionprofileuikit.model;

import android.view.View;

/**
 * Created by World Of UI/UX on 17/4/19.
 */
public class collection {
    private String title;
    private String description;
    private int Tag;
    private int image;

    public collection(String title, String description, int image,int Tag) {
        this.title = title;
        this.description = description;
        this.Tag = Tag;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTag(){ return Tag; }
    public void setTag(int Tag){ this.Tag = Tag; }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
