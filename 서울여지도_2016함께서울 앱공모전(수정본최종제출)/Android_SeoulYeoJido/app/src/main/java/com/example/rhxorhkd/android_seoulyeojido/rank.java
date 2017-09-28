package com.example.rhxorhkd.android_seoulyeojido;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.rhxorhkd.android_seoulyeojido.Model.RankItem;
import com.example.rhxorhkd.android_seoulyeojido.RankRecyclerView.RankAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class rank extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private final ArrayList<RankItem> list = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");
        final FirebaseUser user = auth.getCurrentUser();

        findViewById(R.id.find_myRank).setOnClickListener(this);


        final Comparator<RankItem> sort = new Comparator<RankItem>() {
            public int compare(RankItem r1, RankItem r2) {
                return r1.getChk_cnt().compareTo(r2.getChk_cnt())>0 ? -1: 1;
            }
        };






        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot post: data.getChildren()) {

                    int cnt = list.size();


                    String nick = "" + post.child("nickname").getValue();
                    String chk_cnt = "" + post.child("checkin").getChildrenCount();
                    String img = "" + post.child("profile").getValue();

                    RankItem item = new RankItem(
                            "" + cnt , nick, chk_cnt, ""+post.getKey(), img
                    );
                    list.add(cnt, item);
                    Collections.sort(list, sort);


                    if(cnt+1 == data.getChildrenCount()){
                        mRecyclerView = (RecyclerView) findViewById(R.id.rank_rc);
                        mLayoutManager = new LinearLayoutManager(rank.this, LinearLayoutManager.VERTICAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new RankAdapter(rank.this, list);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ref.child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildChanged(DataSnapshot data, String s) {//내 닉네임 변경 감지

                if(data.getKey().toString().equals("nickname")){
                    for(int i = 0 ; i<list.size();  i++){
                        if(list.get(i).getEct().equals(user.getUid().toString())){
                            list.set(i, new RankItem(""+list.get(i).getRank(),""+data.getValue(), ""+list.get(i).getChk_cnt(), ""+list.get(i).getEct(), ""+list.get(i).getImg()));
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }

                }
                else if(data.getKey().toString().equals("profile")){
                    for(int i = 0 ; i<list.size();  i++){
                        if(list.get(i).getEct().equals(user.getUid().toString())){
                            list.set(i, new RankItem(""+list.get(i).getRank(),""+list.get(i).getNickname(), ""+list.get(i).getChk_cnt(), ""+list.get(i).getEct(), ""+data.getValue()));
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public  void goMypage(){
        TabHost host = (TabHost) this.getParent().findViewById(R.id.tabhost);
        host.setCurrentTab(3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_myRank:

                FirebaseUser user = auth.getCurrentUser();
                for(int i = 0; i <list.size(); i++){
                    if(list.get(i).getEct().equals(user.getUid().toString())){
                        mLayoutManager.smoothScrollToPosition(mRecyclerView, null, i);
//                        mLayoutManager.scrollToPosition(i);
                        break;
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("rank Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }



}
