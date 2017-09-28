package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.rhxorhkd.android_seoulyeojido.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllreviewActivity extends AppCompatActivity {

    private RecyclerView lecyclerView02;
    String result = null;
    JSONArray reviewarray;
    JSONObject object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allreview);
        result = getIntent().getStringExtra("result");
        Log.d("list","-->"+result);
        try {
            object = new JSONObject(result);
            reviewarray = object.getJSONArray("loca_review");
        }catch (Exception e){
            e.printStackTrace();
        }
        initLayout();       //댓글 초기화
        initData(reviewarray);         //댓글
    }

    /**
     * 데이터 초기화
     */
    private void initData(JSONArray array) {

        List<DetailReview> reviewList = new ArrayList<>();
        Log.d("list","---->"+array.length());
        for(int i=0;i<array.length();i++){
            try {
                JSONObject tempobject = array.getJSONObject(i);
                String user_id = tempobject.getString("user_id");
                String review_content = tempobject.getString("review_content");
                String date = tempobject.getString("date");
                DetailReview review = new DetailReview(user_id,review_content,date,R.drawable.irene3);
                reviewList.add(review);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

       /* for (int i = 0; i < 10; i++) {

            DetailReview review = new DetailReview("사용자명 " + (i + 1),"리뷰리뷰 써주세요 " + (i + 1),R.drawable.irene3);
//            review.setImage(R.drawable.irene3);
//            review.setTitle("사용자명 " + (i + 1));
//            review.setReview("리뷰리뷰 써주세요 " + (i + 1));
            reviewList.add(review);
        }*/
        lecyclerView02.setAdapter(new DetailRecyclerAdapter(reviewList, R.layout.detail_row));
        lecyclerView02.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lecyclerView02.setItemAnimator(new DefaultItemAnimator());
    }


    private void initLayout() {

        lecyclerView02 = (RecyclerView) findViewById(R.id.recyclerView2);
    }

}


