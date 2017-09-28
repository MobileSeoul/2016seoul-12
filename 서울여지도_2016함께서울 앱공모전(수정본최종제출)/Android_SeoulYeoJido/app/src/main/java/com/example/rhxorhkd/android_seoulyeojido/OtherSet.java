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
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.AnalysisFragment;
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.OtherAnalysisFragment;
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.OtherVisitedFragment;
import com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment.VisitedFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OtherSet extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase db;
    private DatabaseReference ref;


    private ImageView iv, iv2;
    private TextView tv, tv2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_set);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");

        TabLayout tabLayout = (TabLayout)findViewById(R.id.other_tl_tab);
        ViewPager viewPager = (ViewPager)findViewById(R.id.other_vp_pager);

        Fragment[] fragments = new Fragment[2];
        fragments[0] = new OtherAnalysisFragment();
        fragments[1] = new OtherVisitedFragment();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.other_gotoMap).setOnClickListener(this);
        findViewById(R.id.other_back_btn).setOnClickListener(this);

        iv = (ImageView)findViewById(R.id.other_profile_img);
        iv2 = (ImageView)findViewById(R.id.other_back_btn);
        tv = (TextView)findViewById(R.id.other_down_name);
        tv2 = (TextView)findViewById(R.id.other_up_name);



        Intent i = getIntent();
        tv.setText(i.getStringExtra("name"));
        Glide.with(OtherSet.this).load(i.getStringExtra("profile")).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(this.getView().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });




        if(i.getStringExtra("uid") == null) {


            ref.child(i.getStringExtra("uid")).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot data, String s) {
                    if (data.getKey().toString().equals("nickname")) {
                        tv.setText("" + data.getValue());
                    } else if (data.getKey().toString().equals("profile")) {
                        Glide.with(OtherSet.this).load(data.getValue()).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
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
                    if (data.getKey().toString().equals("nickname")) {
                        tv.setText("" + data.getValue());
                    } else if (data.getKey().toString().equals("profile")) {
                        Glide.with(OtherSet.this).load(data.getValue()).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
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
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.other_back_btn :
                finish();
                break;
            case R.id.other_gotoMap :
                Intent lastintent = getIntent();
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("uid", lastintent.getStringExtra("uid"));
                startActivity(intent);
                break;
            case R.id.other_profile_img :
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
