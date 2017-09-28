package com.example.rhxorhkd.android_seoulyeojido;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.CheckinmapActivity;

public class MainActivity extends ActivityGroup {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent;
        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup(getLocalActivityManager()); //intent를 content로 하기 위해서 getlocalActivitymanager 필요
        TabHost.TabSpec spec;
        Resources res= getResources();
        //setindicator -> 탭에 지시자로, 라벨과 아이콘 지정, setIndicator(CharSequence label, Drawable icon)
        //Setcontent -> 탭의 내용물 XML 문서의 뷰 이용, setContent(Intent intent) -> 인텐트를 이용하여 다른 액티비티 호출

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(26 * dm.density);
        int topbottom = Math.round(9 * dm.density);

        final ImageView tab_home = new ImageView(this);
        tab_home.setImageResource(R.drawable.tab1_selector);
        tab_home.setPadding(size, topbottom, size, topbottom);


        final ImageView tab_checkin = new ImageView(this);
        tab_checkin.setImageResource(R.drawable.tab2_selector);
        tab_checkin.setPadding(size, topbottom, size, topbottom);

        final ImageView tab_rank = new ImageView(this);
        tab_rank.setImageResource(R.drawable.tab3_selector);
        tab_rank.setPadding(size, topbottom, size, topbottom);

        final ImageView tab_my = new ImageView(this);
        tab_my.setImageResource(R.drawable.tab4_selector);
        tab_my.setPadding(size, topbottom, size, topbottom);


        //Drawable d = ContextCompat.getDrawable(this,R.drawable.home);
        //Drawable c = ContextCompat.getDrawable(this,R.drawable.checkin);
        // Drawable b = ContextCompat.getDrawable(this,R.drawable.rank);
        // Drawable a = ContextCompat.getDrawable(this,R.drawable.my);
        //spec = tabHost.newTabSpec("Tab4").setContent(intent).setIndicator("",a);

        intent = new Intent(this,home.class);
        spec = tabHost.newTabSpec("Tab1").setContent(intent).setIndicator(tab_home);
        tabHost.addTab(spec);

        intent = new Intent(this,CheckinmapActivity.class);
        spec = tabHost.newTabSpec("Tab2").setContent(intent).setIndicator(tab_checkin);
        tabHost.addTab(spec);

        intent = new Intent(this,rank.class);
        spec = tabHost.newTabSpec("Tab3").setContent(intent).setIndicator(tab_rank);
        tabHost.addTab(spec);

        intent = new Intent(this,set.class);
        spec = tabHost.newTabSpec("Tab4").setContent(intent).setIndicator(tab_my);
        tabHost.addTab(spec);

        //높이설정
        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height=168;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height=168;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height=168;
        tabHost.getTabWidget().getChildAt(3).getLayoutParams().height=168;


        tabHost.setCurrentTab(0); //첫 시작
    }

    public void switchTab(int i){
        tabHost.setCurrentTab(i);
    }
}
