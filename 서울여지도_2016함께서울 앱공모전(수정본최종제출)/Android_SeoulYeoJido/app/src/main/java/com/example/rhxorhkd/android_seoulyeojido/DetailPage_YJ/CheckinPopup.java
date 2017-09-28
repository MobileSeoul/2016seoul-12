package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.rhxorhkd.android_seoulyeojido.ChangeInfo;
import com.example.rhxorhkd.android_seoulyeojido.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hanyoojin on 2016. 10. 27..
 */

public class CheckinPopup extends Activity implements View.OnClickListener {


    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private OkHttpClient client = new OkHttpClient();
    private Request request;
    private Response response;

    String result;
    String title;
    String uid;
    String dates;
    Intent intent;
    JSONObject object;
    JSONArray array;
    String lat;
    String lon;
    String url;
    String guNumber;
    String category_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 삭제
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinpopup);
         intent = getIntent();
        result = intent.getStringExtra("result");
        title = intent.getStringExtra("title");

        findViewById(R.id.btn_checkincancel).setOnClickListener(this);
        findViewById(R.id.btn_checkin).setOnClickListener(this);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
        dates= df.format(date).toString();

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");

    }

    public class showDataDetail extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            try {
                json.put("loca_name", params[0]);
            }catch(Exception e){
                e.printStackTrace();
            }
            RequestBody posData = RequestBody.create(JSON,json.toString());
            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/showDetailLoca")
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


    public class addcheckin extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            try {
                json.put("user_id", params[0]);
                json.put("loca_name",params[1]);
                json.put("date",params[2]);
            }catch(Exception e){
                e.printStackTrace();
            }
            RequestBody posData = RequestBody.create(JSON,json.toString());
            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/addCheckin")
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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_checkincancel:
                this.finish();
                break;
            case R.id.btn_checkin:


                FirebaseUser user = auth.getCurrentUser();
                uid = user.getUid();

                if(result.equals("1")){
                    showDataDetail showDataDetail = new showDataDetail();
                    String getdata;
                    try{
                        getdata = showDataDetail.execute(title).get();
                        object = new JSONObject(getdata);

                        lat = object.getString("loca_latitude"); //latitude
                        lon = object.getString("loca_longitude"); //longitude

                        array = object.getJSONArray("loca_photo");
                        guNumber = object.getString("loca_guNum"); //구넘버
                        category_num = object.getString("loca_categorynum"); //카테고리
                        Log.d("list",""+category_num);
                        url =array.get(0).toString(); //사진


                        Map<String, Object> chekindb = new HashMap<>();
                        chekindb.put("category",category_num);
                        chekindb.put("guNumber", guNumber);
                        chekindb.put("date", dates);
                        chekindb.put("img", url);
                        chekindb.put("lat", lat);
                        chekindb.put("lon", lon);

                        ref.child(user.getUid()+"/checkin/"+title).setValue(chekindb);



                        Log.d("list"," lat : "+lat+" lon : "+lon+" guNumber : "+guNumber+" url : "+url);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


                if(result.equals("1")){
                    addcheckin addcheckin = new addcheckin();
                    try{
                    addcheckin.execute(uid,title,dates).get();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
//                    Intent i = new Intent(this, CheckinOK.class);
//                    i.putExtra("name", title);
//                    startActivity(i);
                startActivity(new Intent(this, CheckinOK.class));
                }
                else startActivity(new Intent(this, CheckinFail.class));

                finish();

                break;
            default: break;
        }
    }
}


//public class Dialog extends Activity implements View.OnClickListener {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 삭제
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog);
//
//        findViewById(R.id.btn_reviewCancel).setOnClickListener(this);
//    }
//
//    public void onClick(View v){
//        switch (v.getId()){
//            case R.id.btn_reviewCancel:
//                this.finish();
//                break;
//            case R.id.btn_reviewSave:
//
//        }
//
//    }
//}

