package com.example.rhxorhkd.android_seoulyeojido.Model;

/**
 * Created by hanyoojin on 2016. 10. 27..
 */

public class CheckinItem {
    String guNum;
    String postion;
    String title;
    String url;
    String date;

    public CheckinItem(String guNum, String postion, String title, String url, String date) {
        this.guNum = guNum;
        this.postion = postion;
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getPostion() {
        return postion;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getGuNum() {
        return guNum;
    }
}
