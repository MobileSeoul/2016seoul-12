package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Intent;
import android.graphics.Bitmap;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.rhxorhkd.android_seoulyeojido.Model.RankItem;
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.AnalysisFragment;
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.BookmarkFragment;
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.VisitedFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class set extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase db;
    private DatabaseReference ref;


    private ImageView iv, iv2;
    private TextView tv, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");


//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("아이린");

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tl_tab);
        ViewPager viewPager = (ViewPager)findViewById(R.id.vp_pager);

        Fragment[] fragments = new Fragment[2];
        fragments[0] = new AnalysisFragment();
        fragments[1] = new VisitedFragment();
//        fragments[2] = new BookmarkFragment();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.getChildAt(0).setBackgroundResource(R.color.black);
//        viewPager.getChildAt(1).setBackgroundResource(R.color.black);

        findViewById(R.id.gotoMap).setOnClickListener(this);

        iv = (ImageView)findViewById(R.id.my_profile_img);
        iv2 = (ImageView)findViewById(R.id.back_btn);
        tv = (TextView)findViewById(R.id.down_name);
        tv2 = (TextView)findViewById(R.id.up_name);

        Intent i = getIntent();
        if(user.getUid() != null){//탭으로 본 마이페이지
            findViewById(R.id.my_profile_img).setOnClickListener(this);//
            iv2.setImageDrawable(null);//백버튼 없애기

            ref.child(user.getUid()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot data, String s) {
                    if(data.getKey().toString().equals("nickname")){
                        tv.setText(""+data.getValue());
                    }
                    else if(data.getKey().toString().equals("profile")){
                        Glide.with(set.this).load(data.getValue()).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv){
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(this.getView().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                iv.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot data, String s) {
                    if(data.getKey().toString().equals("nickname")){
                        tv.setText(""+data.getValue());
                    }
                    else if(data.getKey().toString().equals("profile")){
                        Glide.with(set.this).load(data.getValue()).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv){
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(this.getView().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                iv.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn :
                finish();
                break;
            case R.id.gotoMap :
                FirebaseUser user = auth.getCurrentUser();
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("uid", user.getUid());
                startActivity(intent);
                break;
            case R.id.my_profile_img :
                Intent i = new Intent(this, ChangeInfo.class);
                i.putExtra("nickname", tv.getText());
                startActivity(i);
                break;
            default: break;
        }
    }





    private class MyPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments;

        public MyPagerAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return  "나의 서울";
                case 1 :
                    return "체크인";
//                case 2 :
//                    return "담은 서울";
                default:
                    return "";
            }
        }
    }
}
