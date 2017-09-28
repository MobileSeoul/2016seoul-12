package com.example.rhxorhkd.android_seoulyeojido;

/**
 * Created by rhxorhkd on 2016-10-15.
 */

public class Listviewitem {
    private String icon;
    private String name;
    private String subname;

    private String thirdname;
    private String fourthname;


    public String getIcon() { return icon; }

    public String getSubname() {
        return subname;
    }

    public String getThirdname() {
        return thirdname;
    }



    public String getFourthname() {
        return fourthname;
    }

    public String getName() {
        return name;
    }
    public Listviewitem(String icon,String name,String subname, String thirdname, String fourthname){
        this.icon=icon;
        this.name=name;
        this.subname=subname;
        this.thirdname=thirdname;

        this.fourthname=fourthname;
    }

}
