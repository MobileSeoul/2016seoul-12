package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.rhxorhkd.android_seoulyeojido.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hanyoojin on 2016. 10. 25..
 */

public class Dialog extends Activity implements View.OnClickListener {
    EditText editText;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    String id;
    String locationTitle;
    String date;
    Request request;
    Response response;
    Intent intent;
    String result;
    JSONObject object;
    JSONArray reviewarray;
    OkHttpClient client = new OkHttpClient();

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

    public void showdetailinit(){
        showDataDetail showDataDetail = new showDataDetail();
        try {
            Log.d("list","aaa"+locationTitle);
            result = showDataDetail.execute(locationTitle).get();
           /* object = new JSONObject(result);
            reviewarray = object.getJSONArray("loca_review");*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class reviewadd extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            try {
                json.put("user_id", params[0]);
                json.put("content",params[1]);
                json.put("loca_name",params[2]);
                json.put("date",params[3]);
            }catch(Exception e){
                e.printStackTrace();
            }
            RequestBody posData = RequestBody.create(JSON,json.toString());
            request = new Request.Builder()
                    .url("http://52.78.32.50:3000/ko/addReview")
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 삭제
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
        Date newdate = new Date();
        date = df.format(newdate);
        intent = getIntent();
        locationTitle = intent.getStringExtra("title");

        editText = (EditText) findViewById(R.id.edt_review);
        findViewById(R.id.btn_reviewCancel).setOnClickListener(this);
        findViewById(R.id.btn_reviewSave).setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        id = user.getUid();

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_reviewCancel:
                this.finish();
                break;
            case R.id.btn_reviewSave:
                String content = editText.getText().toString();
                Log.d("list","id : "+id+"  content  : "+content+" title : "+locationTitle+"  date : "+date);
                reviewadd reviewadd = new reviewadd();
                reviewadd.execute(id,content,locationTitle,date);
                finish();
                break;

        }
    }



}
