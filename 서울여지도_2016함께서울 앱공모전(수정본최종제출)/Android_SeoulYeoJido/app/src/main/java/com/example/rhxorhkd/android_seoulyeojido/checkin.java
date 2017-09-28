package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.CheckinmapActivity;
import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.DetailActivity;

import java.util.ArrayList;

public class checkin extends AppCompatActivity {
    ListView lv; //리스트
    ListviewAdapter adapter; //listview adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

       // startActivity(new Intent(getApplicationContext(), CheckinmapActivity.class));




        //listInit(); //리스트 초기화

    }
//    public void listInit(){
//        lv =(ListView) findViewById(R.id.list);
//
//        final ArrayList<Listviewitem> data = new ArrayList<>();
//       /* Listviewitem data1 = new Listviewitem(R.drawable.ic_launcher,"one","ones","oness",R.drawable.heart1);
//        Listviewitem data2 = new Listviewitem(R.drawable.ic_launcher,"two","twos","twoss",R.drawable.heart1);
//        Listviewitem data3 = new Listviewitem(R.drawable.ic_launcher,"three","threes","thress",R.drawable.heart1);
//        Listviewitem data4 = new Listviewitem(R.drawable.ic_launcher,"four","fours","fourss",R.drawable.heart1);
//        Listviewitem data5 = new Listviewitem(R.drawable.ic_launcher,"five","fives","fivess",R.drawable.heart1);
//        Listviewitem data6 = new Listviewitem(R.drawable.ic_launcher,"six","sixs","sixss",R.drawable.heart1);
//        Listviewitem data7 = new Listviewitem(R.drawable.ic_launcher,"seven","sevens","sevenss",R.drawable.heart1);
//        Listviewitem data8 = new Listviewitem(R.drawable.ic_launcher,"eight","eights","eightss",R.drawable.heart1);
//        Listviewitem data9 = new Listviewitem(R.drawable.ic_launcher,"nine","nines","niness",R.drawable.heart1);
//        Listviewitem data10 = new Listviewitem(R.drawable.ic_launcher,"ten","tens","tenss",R.drawable.heart1);
//        Listviewitem data11 = new Listviewitem(R.drawable.ic_launcher,"eleven","elevens","envenss",R.drawable.heart1);
//
//        data.add(data1);
//        data.add(data2);
//        data.add(data3);5
//        data.add(data4);
//        data.add(data5);
//        data.add(data6);
//        data.add(data7);
//        data.add(data8);
//        data.add(data9);
//        data.add(data10);
//        data.add(data11);*/
//
//        adapter = new ListviewAdapter(this,R.layout.item,data);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //String str = (String)adapter.getItem(position);
////                //Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG).show(); //옮기고 이거써
////                Toast.makeText(checkin.this,str,Toast.LENGTH_LONG).show();
//
//                // DetailActivity 로 연결
//                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
//                intent.putExtra("name", data.get(position).getName());
//                startActivity(intent);
//            }
//        });
//
//    }

}
