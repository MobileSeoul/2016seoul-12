package com.example.rhxorhkd.android_seoulyeojido;

/**
 * Created by rhxorhkd on 2016-10-17.
 */

public class Searchitem {
    private String name;
    private int icon;
    private int state; //0일때 장소 //1일때 주소

    public String getName() {
        return name;
    }

    public int getIcon() {return icon;}

    public int getState() {return state;}

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Searchitem(String name,int icon, int state){
        this.name=name;
        this.icon=icon;
        this.state=state;
    }
}
