package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

/**
 * Created by YJ on 2016-10-25.
 */

public class DetailReview {
    private String username;
    private String review;
    private String date;
    private int image;

    public String getTitle() {
        return username;
    }

    public void setTitle(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public DetailReview(String username, String review, String date,int image){
        this.username=username;
        this.review=review;
        this.date =date;
        this.image=image;
    }
}