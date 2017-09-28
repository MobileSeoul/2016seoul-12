package com.example.rhxorhkd.android_seoulyeojido.Model;

/**
 * Created by 병윤 on 2016-10-24.
 */

public class VisitedItem {
    String photo;
    String title;
    String cnt;
    String category;
    String guName;

    public VisitedItem(String photo, String title, String cnt, String category, String guName) {
        this.photo = photo;
        this.title = title;
        this.cnt = cnt;
        this.category = category;
        this.guName = guName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public String getCnt() {
        return cnt;
    }

    public String getCategory() {
        return category;
    }

    public String getGuName() {
        return guName;
    }
}
