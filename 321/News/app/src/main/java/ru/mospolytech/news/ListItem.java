package ru.mospolytech.news;

import android.net.Uri;

public class ListItem {
    public Uri image;
    public String title;
    public String text;

    public ListItem(Uri image, String title, String text){
        this.image = image;
        this.title = title;
        this.text = text;
    }
}
