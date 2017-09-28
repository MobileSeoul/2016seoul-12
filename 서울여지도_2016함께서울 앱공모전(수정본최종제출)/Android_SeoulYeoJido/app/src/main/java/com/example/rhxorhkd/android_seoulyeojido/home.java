package com.example.rhxorhkd.android_seoulyeojido;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.DetailActivity;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class home extends AppCompatActivity {
    boolean open = false;
    ActionBar actionBar;
    ArrayList<Listviewitem> data;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseUser user;


    ImageView actionbarlogo;
    ImageView mapview1; //맵 이미지
    ImageView mapview2;
    ImageView mapview3;
    ImageView mapview4;
    ImageView mapview5;
    ImageView mapview6;
    ImageView mapview7;
    ImageView mapview8;
    ImageView mapview9;
    ImageView mapview10;
    ImageView mapview11;
    ImageView mapview12;


    ImageView imageView6; //카테고리 이미지
    ImageView imageView5;
    ImageView imageView4;
    ImageView imageView3;
    ImageView imageView2;
    ImageView imageView;

    ListviewAdapter adapter; //listview adapter
    SearchitemAdapter sadapter; //search adapter
    RelativeLayout map; //지도
    LinearLayout searchlistview; //검색창누르면 나오는 리스트뷰
    SearchView searchView; //서치뷰
    ListView lv; //리스트
    ArrayList<Searchitem> searchdatas; //서치 데이터들
    ListView listView1; //서치 리스트뷰들
    OkHttpClient client;
    private static final String TAG = "DemoActivity";
    private SlidingUpPanelLayout mLayout;
    JSONObject jsonobject;
    JSONArray jsonarray;
    Response response;
    Request request;
    int guNumber_1, guNumber_2, guNumber_3, guNumber_4, guNumber_5, guNumber_6, guNumber_7,
            guNumber_8, guNumber_9, guNumber_10, guNumber_11;
    LinearLayout hometitle;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        client = new OkHttpClient();
        map=(RelativeLayout)findViewById(R.id.map);
        searchlistview=(LinearLayout)findViewById(R.id.searchlistview);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        ref = db.getReference("member").child(user.getUid()+"/checkin");

        CheckTypesTask task = new CheckTypesTask(); //로딩(구현필요)
        task.execute();


        // 액션바 디자인
        actionBar = getSupportActionBar();
        actionBar.setElevation(0); // 그림자 없애기
        actionBar.setCustomView(R.layout.hometitle);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionbarlogo = (ImageView)findViewById(R.id.actionBarLogo);
       //Glide.with(this).load(R.drawable.logo).into(actionbarlogo);


        imageinit();  //이미지 로드 , 리스너 추가


        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"NotoSans-Regular.ttf"));  //글씨체

        firstListGetData getData = new firstListGetData(); //전체 리스트 가져오기
        String result = null;
        try {
            result = getData.execute().get();
            JSONObject object = new JSONObject(result);
            JSONArray jsonArray = object.getJSONArray("location");
            listInit(jsonArray);
            searchInit(jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        //listInit(); //리스트 초기화


        mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG,"onPanelSlide, offset "+slideOffset);
                if(slideOffset==0.0){
                    Log.d("list","닫음");
                    actionbarchanged(Integer.parseInt("0"));}

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG,"onPanelStateChanged" + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                open=false;
                actionbarchanged(Integer.parseInt("0"));
            }
        });


        //체크인 기록 불러오기
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot data, String s) {
                if(data.child("guNumber").getValue()!=null)
                {
                    switch (Integer.parseInt(""+data.child("guNumber").getValue())){
                        case 1 :
                            guNumber_1++;
                            break;
                        case 2 :
                            guNumber_2++;
                            break;
                        case 3 :
                            guNumber_3++;
                            break;
                        case 4 :
                            guNumber_4++;
                            break;
                        case 5 :
                            guNumber_5++;
                            break;
                        case 6 :
                            guNumber_6++;
                            break;
                        case 7 :
                            guNumber_7++;
                            break;
                        case 8 :
                            guNumber_8++;
                            break;
                        case 9 :
                            guNumber_9++;
                            break;
                        case 10 :
                            guNumber_10++;
                            break;
                        case 11 :
                            guNumber_11++;
                            break;

                        default: break;
                    }
                }
              gustage();

                Log.d("영등포구 체크인 수: ", ""+guNumber_10);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        //
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); //gps 사용 유/무 파악하려고
        if(!locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){ //gps 꺼져있을 시 앱이 수행할 코드
            Log.d("list","gps 연결 X");
            new AlertDialog.Builder(home.this)
                    .setMessage("GPS가 꺼져있습니다. \n 'Google 위치 서비스' 를 체크해주세요")
                    .setPositiveButton("설정", new DialogInterface.OnClickListener() { //설정 버튼 누를때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); //설정페이지 이동
                            startActivity(intent);
                        }
                    }).setNegativeButton("취소",null).show();

        }


        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstListGetData getData = new firstListGetData(); //전체 리스트 가져오기
                String result = null;
                try {
                    result = getData.execute().get();
                    JSONObject object = new JSONObject(result);
                    JSONArray jsonArray = object.getJSONArray("location");
                    listInit(jsonArray);

                }catch (Exception e){
                    e.printStackTrace();
                }

                if(open){
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    open=false;
                }else{
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    open=true;
                }



            }
        });

    }
//1.은평 1   ,  2.마포 4   3.종로 21   4.성북 1  5.강북 1    6. 중랑0    7.송파 2   8. 서초3   9.관악 0  10.영등포  5,  11.강서 0
    public void gustage(){
        Log.d("list","gggggg");
       guone();
        gutwo();
        guthree();
        gufour();
        gufive();
        gusix();
        guseven();
        gueight();
        gunine();
        guten();
        gueleven();
    }

    public void gueleven(){

        int tempper=guNumber_11*100/1 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_eleven_1).into(mapview11);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_eleven_2).into(mapview11);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_eleven_3).into(mapview11);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_eleven_4).into(mapview11);
        }else{
            Glide.with(this).load(R.drawable.map_eleven_5).into(mapview11);
        }
    }
    public void guten(){
        int tempper=guNumber_10*100/5 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_ten_1).into(mapview10);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_ten_2).into(mapview10);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_ten_3).into(mapview10);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_ten_4).into(mapview10);
        }else{
            Glide.with(this).load(R.drawable.map_ten_5).into(mapview10);
        }
    }
    public void gunine(){

        int tempper=guNumber_9*100/1 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_nine_1).into(mapview9);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_nine_2).into(mapview9);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_nine_3).into(mapview9);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_nine_4).into(mapview9);
        }else{
            Glide.with(this).load(R.drawable.map_nine_5).into(mapview9);
        }
    }
    public void gueight(){

        int tempper=guNumber_8*100/3;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_eight_1).into(mapview8);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_eight_2).into(mapview8);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_eight_3).into(mapview8);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_eight_4).into(mapview8);
        }else{
            Glide.with(this).load(R.drawable.map_eight_5).into(mapview8);
        }

    }
    public void guseven(){

        int tempper =guNumber_7*100/2;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_seven_1).into(mapview7);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_seven_2).into(mapview7);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_seven_3).into(mapview7);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_seven_4).into(mapview7);
        }else{
            Glide.with(this).load(R.drawable.map_seven_5).into(mapview7);
        }

    }
    public void gusix(){

        int i;
        int tempper =guNumber_6*100/1;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_six_1).into(mapview6);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_six_2).into(mapview6);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_six_3).into(mapview6);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_six_4).into(mapview6);
        }else{
            Glide.with(this).load(R.drawable.map_six_5).into(mapview6);
        }
    }
    public void gufive(){

        int i;
        int tempper=guNumber_5*100/1 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_five_1).into(mapview5);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_five_2).into(mapview5);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_five_3).into(mapview5);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_five_4).into(mapview5);
        }else{
            Glide.with(this).load(R.drawable.map_five_5).into(mapview5);
        }
    }
    public void gufour(){

        int i;
        int tempper =guNumber_4*100/1;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_four_1).into(mapview4);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_four_2).into(mapview4);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_four_3).into(mapview4);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_four_4).into(mapview4);
        }else{
            Glide.with(this).load(R.drawable.map_four_5).into(mapview4);
        }

    }
    public void guone(){

        int i;
        int tempper=guNumber_1*100/1 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_one_1).into(mapview1);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_one_2).into(mapview1);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_one_3).into(mapview1);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_one_4).into(mapview1);
        }else{
            Glide.with(this).load(R.drawable.map_one_5).into(mapview1);
        }
    }
    public void gutwo(){

        int i;
        int tempper=guNumber_2*100/4 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_two_1).into(mapview2);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_two_2).into(mapview2);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_two_3).into(mapview2);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_two_4).into(mapview2);
        }else{
            Glide.with(this).load(R.drawable.map_two_5).into(mapview2);
        }
    }
    public void guthree(){
        int per = 100;
        int i;
        int tempper=guNumber_3*100/21 ;

        if(tempper==0){
            Glide.with(this).load(R.drawable.map_three_1).into(mapview3);
        }else if(tempper<=33){
            Glide.with(this).load(R.drawable.map_three_2).into(mapview3);
        }else if(tempper<=66){
            Glide.with(this).load(R.drawable.map_three_3).into(mapview3);
        }else if(tempper<=99){
            Glide.with(this).load(R.drawable.map_three_4).into(mapview3);
        }else{
            Glide.with(this).load(R.drawable.map_three_5).into(mapview3);
        }

    }


    class guListner implements View.OnClickListener{
        String guNum;
        public guListner(String guNum){
            this.guNum=guNum;
        }
        @Override
        public void onClick(View v) {
            client = new OkHttpClient();
            guListGetData getData = new guListGetData();
            String result = null;
            try{
                actionbarchanged(Integer.parseInt(guNum));
                result = getData.execute(guNum).get();
                jsonobject = new JSONObject(result);
                jsonarray = jsonobject.getJSONArray("location");
                listInit(jsonarray);
            }catch (Exception e){
                e.printStackTrace();
            }
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            open=true;
        }
    }

    class MyListner implements View.OnClickListener{
        String categoryNum;
        public MyListner(String categoryNum) {
            this.categoryNum = categoryNum;
        }
        @Override
        public void onClick(View v) {
            client = new OkHttpClient();
            categoryListGetData getData = new categoryListGetData();
            String result = null;
            try{
                result = getData.execute(categoryNum).get();
                jsonobject = new JSONObject(result);
                jsonarray = jsonobject.getJSONArray("location");
                listInit(jsonarray);
            }catch (Exception e){
                e.printStackTrace();
            }
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            open=true;

        }
    }

    public void actionbarchanged(int gunum){
         //actionbarlogo = (ImageView)findViewById(R.id.actionBarLogo);
        //       Glide.with(this).load(R.drawable.map_one_1).into(mapview1);
        Log.d("list","gunum : "+gunum);
        if(gunum==1){
            Glide.with(this).load(R.drawable.logo_one).into(actionbarlogo);
        }else if(gunum==2){
            Glide.with(this).load(R.drawable.logo_two).into(actionbarlogo);
        }else if(gunum==3){
            Glide.with(this).load(R.drawable.logo_three).into(actionbarlogo);
        }else if(gunum==4){
            Glide.with(this).load(R.drawable.logo_four).into(actionbarlogo);
        }else if(gunum==5){
            Glide.with(this).load(R.drawable.logo_five).into(actionbarlogo);
        }else if(gunum==6){
            Glide.with(this).load(R.drawable.logo_six).into(actionbarlogo);
        }else if(gunum==7){
            Glide.with(this).load(R.drawable.logo_seven).into(actionbarlogo);
        }else if(gunum==8){
            Glide.with(this).load(R.drawable.logo_eight).into(actionbarlogo);
        }else if(gunum==9){
            Glide.with(this).load(R.drawable.logo_nine).into(actionbarlogo);
        }else if(gunum==10){
            Glide.with(this).load(R.drawable.logo_ten).into(actionbarlogo);
        }else if(gunum==11){
            Glide.with(this).load(R.drawable.logo_eleven).into(actionbarlogo);
        }else
           Glide.with(this).load(R.drawable.titlelogo).into(actionbarlogo);
    }

    public void imageinit(){
        mapview1 = (ImageView)findViewById(R.id.mapone);
        mapview2= (ImageView)findViewById(R.id.maptwo);
        mapview3= (ImageView)findViewById(R.id.mapthree);
        mapview4= (ImageView)findViewById(R.id.mapfour);
        mapview5= (ImageView)findViewById(R.id.mapfive);
        mapview6= (ImageView)findViewById(R.id.mapsix);
        mapview7= (ImageView)findViewById(R.id.mapseven);
        mapview8= (ImageView)findViewById(R.id.mapeight);
        mapview9= (ImageView)findViewById(R.id.mapnine);
        mapview10= (ImageView)findViewById(R.id.mapten);
        mapview11= (ImageView)findViewById(R.id.mapeleven);
        mapview12= (ImageView)findViewById(R.id.mapriver);


        Glide.with(this).load(R.drawable.map_one_1).into(mapview1);
        Glide.with(this).load(R.drawable.map_two_1).into(mapview2);
        Glide.with(this).load(R.drawable.map_three_1).into(mapview3);
        Glide.with(this).load(R.drawable.map_four_1).into(mapview4);
        Glide.with(this).load(R.drawable.map_five_1).into(mapview5);
        Glide.with(this).load(R.drawable.map_six_1).into(mapview6);
        Glide.with(this).load(R.drawable.map_seven_1).into(mapview7);
        Glide.with(this).load(R.drawable.map_eight_1).into(mapview8);
        Glide.with(this).load(R.drawable.map_nine_1).into(mapview9);
        Glide.with(this).load(R.drawable.map_ten_1).into(mapview10);
        Glide.with(this).load(R.drawable.map_eleven_1).into(mapview11);


        Glide.with(this).load(R.drawable.map).into(mapview12);

        //6 유적지 5 랜드마크  4 전통시장 3  공원 2 문화 1 쇼핑

        imageView6 =(ImageView)findViewById(R.id.imageView6);
        imageView5 = (ImageView)findViewById(R.id.imageView5);
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView = (ImageView)findViewById(R.id.imageView);

        Glide.with(this).load(R.drawable.history).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView6){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView6.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(this).load(R.drawable.landmark).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView5){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView5.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(this).load(R.drawable.markets).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView4){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView4.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(this).load(R.drawable.parks).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView3){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView3.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(this).load(R.drawable.cultures).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView2){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView2.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(this).load(R.drawable.shoping).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });


        mapview1.setOnClickListener(new guListner("1"));
        mapview2.setOnClickListener(new guListner("2"));
        mapview3.setOnClickListener(new guListner("3"));
        mapview4.setOnClickListener(new guListner("4"));
        mapview5.setOnClickListener(new guListner("5"));
        mapview6.setOnClickListener(new guListner("6"));
        mapview7.setOnClickListener(new guListner("7"));
        mapview8.setOnClickListener(new guListner("8"));
        mapview9.setOnClickListener(new guListner("9"));
        mapview10.setOnClickListener(new guListner("10"));
        mapview11.setOnClickListener(new guListner("11"));


        imageView6.setOnClickListener(new MyListner("6"));
        imageView5.setOnClickListener(new MyListner("5"));
        imageView4.setOnClickListener(new MyListner("4"));
        imageView3.setOnClickListener(new MyListner("3"));
        imageView2.setOnClickListener(new MyListner("2"));
        imageView.setOnClickListener(new MyListner("1"));
    }

    public void listInit(JSONArray listarr){
        lv =(ListView) findViewById(R.id.list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        Log.d("list",listarr.toString());
        data = new ArrayList<>();

        for(int i=0;i<listarr.length();i++){
            try{
            JSONObject object = listarr.getJSONObject(i);
             Listviewitem tempdata = new Listviewitem(object.getString("loca_photo"),object.getString("loca_name"),object.getString("loca_checkincount"),object.getString("loca_categorynum"),object.getString("loca_guNum"));
             data.add(tempdata);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        Log.d("list","data.size -> "+data.size());
        adapter = new ListviewAdapter(this,R.layout.item,data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String)adapter.getItem(position);
                //Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG).show(); //옮기고 이거써
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("name",str);
                startActivity(intent);
            }
        });
    }

    public void searchInit(JSONArray array){
        listView1 = (ListView)findViewById(R.id.listview1);
        searchdatas = new ArrayList<>(); //장소일때 주소일때 0,1 구별해서 state 넣기

        for(int i=0;i<array.length();i++){
            try{
                JSONObject object = array.getJSONObject(i);
                Searchitem tempdata = new Searchitem(object.getString("loca_name"),R.drawable.pointer_small,0);
                searchdatas.add(tempdata);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        Searchitem tempdata1  = new Searchitem("은평구",R.drawable.white,1);
        Searchitem tempdata2  = new Searchitem("서대문구",R.drawable.white,1);
        Searchitem tempdata3  = new Searchitem("마포구",R.drawable.white,1);
        Searchitem tempdata4  = new Searchitem("중구",R.drawable.white,1);
        Searchitem tempdata5  = new Searchitem("성북구",R.drawable.white,1);
        Searchitem tempdata6  = new Searchitem("동대문구",R.drawable.white,1);
        Searchitem tempdata7  = new Searchitem("성동구",R.drawable.white,1);
        Searchitem tempdata8  = new Searchitem("강북구",R.drawable.white,1);
        Searchitem tempdata9  = new Searchitem("도봉구",R.drawable.white,1);
        Searchitem tempdata10  = new Searchitem("노원구",R.drawable.white,1);
        Searchitem tempdata11  = new Searchitem("중랑구",R.drawable.white,1);
        Searchitem tempdata12  = new Searchitem("광진구",R.drawable.white,1);
        Searchitem tempdata13  = new Searchitem("송파구",R.drawable.white,1);
        Searchitem tempdata14  = new Searchitem("강동구",R.drawable.white,1);
        Searchitem tempdata15  = new Searchitem("서초구",R.drawable.white,1);
        Searchitem tempdata16  = new Searchitem("강남구",R.drawable.white,1);
        Searchitem tempdata17  = new Searchitem("관악구",R.drawable.white,1);
        Searchitem tempdata18  = new Searchitem("금천구",R.drawable.white,1);
        Searchitem tempdata19  = new Searchitem("영등포구",R.drawable.white,1);
        Searchitem tempdata20  = new Searchitem("동작구",R.drawable.white,1);
        Searchitem tempdata21  = new Searchitem("강서구",R.drawable.white,1);
        Searchitem tempdata22  = new Searchitem("양천구",R.drawable.white,1);
        Searchitem tempdata23  = new Searchitem("구로구",R.drawable.white,1);
        Searchitem tempdata24  = new Searchitem("용산구",R.drawable.white,1);
        Searchitem tempdata25  = new Searchitem("종로구",R.drawable.white,1);
        searchdatas.add(tempdata1);
        searchdatas.add(tempdata2);
        searchdatas.add(tempdata3);
        searchdatas.add(tempdata4);
        searchdatas.add(tempdata5);
        searchdatas.add(tempdata6);
        searchdatas.add(tempdata7);
        searchdatas.add(tempdata8);
        searchdatas.add(tempdata9);
        searchdatas.add(tempdata10);
        searchdatas.add(tempdata11);
        searchdatas.add(tempdata12);
        searchdatas.add(tempdata13);
        searchdatas.add(tempdata14);
        searchdatas.add(tempdata15);
        searchdatas.add(tempdata16);
        searchdatas.add(tempdata17);
        searchdatas.add(tempdata18);
        searchdatas.add(tempdata19);
        searchdatas.add(tempdata20);
        searchdatas.add(tempdata21);
        searchdatas.add(tempdata22);
        searchdatas.add(tempdata23);
        searchdatas.add(tempdata24);
        searchdatas.add(tempdata25);



        sadapter = new SearchitemAdapter(this,R.layout.searchitem,searchdatas);
        listView1.setAdapter(sadapter);
        listView1.setDivider(null);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("text","listview1clicked");
                String str = (String)sadapter.getItem(position);
                String tt[] = str.split(":");
                Log.d("list","tt[1]-->"+tt[1]);
                if(tt[1].equals("0")){
                    Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                    intent.putExtra("name",tt[0]);
                    startActivity(intent);
                }else{
                    searchListGetData searchListGetData = new searchListGetData();
                    String result = null;

                    try{
                        result = searchListGetData.execute(tt[0]).get();
                        jsonobject = new JSONObject(result);
                        jsonarray = jsonobject.getJSONArray("location");
                        Log.d("list","jsonarraysize"+jsonarray.length());
                        listInit(jsonarray);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    open=true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) { //펼쳐있을때 back 누르면 닫히는거
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            open=false;
            actionbarchanged(Integer.parseInt("0"));
        } else {
            super.onBackPressed();
        }
    }

    private Drawable resizeImage(int resId,int w,int h){
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),resId);
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float)newWidth)/width;
        float scaleHeight =  ((float)newHeight)/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg,0,0,width,height,matrix,true);
        return new BitmapDrawable(resizedBitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //search
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_view,menu);
        MenuItem mSearch = menu.findItem(R.id.search);

        mSearch.setIcon(resizeImage(R.drawable.search,150,150));
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItemCompat.setOnActionExpandListener(mSearch, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {  //서치 아이콘 누를때  expand
                Log.d("text","open");
                map.setVisibility(View.INVISIBLE); // map화면 가려짐
                searchlistview.setVisibility(View.VISIBLE); //searchlist 화면 켜짐
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                open=false;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) { //서치 끌때
                Log.d("text","close");
                searchlistview.setVisibility(View.INVISIBLE);
                map.setVisibility(View.VISIBLE);
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                open=false;
                return true;
            }


        });

        searchView  = (SearchView) MenuItemCompat.getActionView(mSearch);

        searchView.setSubmitButtonEnabled(true);
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Log.d("text",query); -> query 찍힘
                searchListGetData searchListGetData = new searchListGetData();
                String result = null;

                try{
                    result = searchListGetData.execute(query).get();
                    jsonobject = new JSONObject(result);
                    jsonarray = jsonobject.getJSONArray("location");
                    Log.d("list","jsonarraysize"+jsonarray.length());
                    listInit(jsonarray);
                }catch (Exception e){
                    e.printStackTrace();
                }

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                open=true;
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null){
                sadapter.filter(newText);
                Log.d("text",""+newText);
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    open=false;
                  }
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    public class firstListGetData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/showloca")
                    .build();

            try{
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public class searchListGetData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            try {
                json.put("searchtext", params[0]);
            }catch(Exception e){
                e.printStackTrace();
            }
            RequestBody posData = RequestBody.create(JSON,json.toString());
            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/searchLocaAddress")
                    .post(posData)
                    .build();
            try{
                response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            //post gu num
            return null;
        }
    }

    public class guListGetData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            RequestBody posData = new FormBody.Builder()
                    .add("type","json")
                    .add("guNum",params[0])
                    .build();

            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/showGuLoca")
                    .post(posData)
                    .build();
            try{
                response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            //post gu num
            return null;
        }
    }

    public class categoryListGetData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            RequestBody posData = new FormBody.Builder()
                    .add("type","json")
                    .add("categoryNum",params[0])
                    .build();

            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/showCategoryLoca")
                    .post(posData)
                    .build();
            try{
                response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            //post gu num
            return null;
        }
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void>{
        ProgressDialog asyncDialog = new ProgressDialog(home.this);
        //customprogressdialog customprogressdialog;

        @Override
        protected void onPreExecute() {
            //customprogressdialog = new customprogressdialog(home.this);
            Log.d("list","execute");
            /*customprogressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customprogressdialog.show();*/
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중");
            asyncDialog.setCanceledOnTouchOutside(false); //진행되는 동안 바깥족을 눌러 종료하는것 금지
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("list","back"); //리스트들이 꽉차면 return
           /* try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("list","post");
        //    customprogressdialog.dismiss();

            asyncDialog.dismiss();
            asyncDialog=null;
            super.onPostExecute(aVoid);
        }
    }


}

