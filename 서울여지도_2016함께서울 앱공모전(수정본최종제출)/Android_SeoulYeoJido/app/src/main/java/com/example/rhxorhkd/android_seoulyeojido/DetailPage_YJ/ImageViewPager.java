package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageViewPager extends AppCompatActivity {

    ImageView back;

    int position;
    OkHttpClient client = new OkHttpClient();
    Response response;
    Request request;
    GridView gridView;
    JSONArray array;
    JSONObject object;

    public class showDataDetail extends AsyncTask<String, Void, String> {
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
      //  actionBar.setDisplayHomeAsUpEnabled(true);
       // actionBar.setDisplayShowTitleEnabled(false);

//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back = (ImageView)findViewById(R.id.back_btn1);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { finish(); }
        });

        Intent p = getIntent();
        position = p.getExtras().getInt("id");
        final String locationtitle = p.getStringExtra("title");
        showDataDetail showDataDetail = new showDataDetail();
        String result =null;
        try {
            result = showDataDetail.execute(locationtitle).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this,result);
        List<ImageView> images = new ArrayList<ImageView>();

        for(int i=0; i<imageGridAdapter.getCount(); i++){
            ImageView imageView = new ImageView(this);
            Glide.with(this).load( imageGridAdapter.mThumblds.get(i)).centerCrop().into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setScaleType(ImageView.ScaleType.CENTER);
            images.add(imageView);
        }

        ImagePagerAdapter pageradapter = new ImagePagerAdapter(images);
        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(pageradapter);

        viewpager.setCurrentItem(position);

    }

    /**
     * 액션바 뒤로가기
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
