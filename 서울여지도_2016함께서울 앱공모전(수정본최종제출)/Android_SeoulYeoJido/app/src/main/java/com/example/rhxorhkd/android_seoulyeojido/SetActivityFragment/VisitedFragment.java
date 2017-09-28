package com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.example.rhxorhkd.android_seoulyeojido.R;
import com.example.rhxorhkd.android_seoulyeojido.RankRecyclerView.RankAdapter;
import com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView.VisitedAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitedFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private FirebaseUser user;
    private DatabaseReference ref;

    public VisitedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visited, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.visited_rc);
        mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");
        user = auth.getCurrentUser();

        final ArrayList<VisitedItem> list = new ArrayList<>();

        mAdapter = new VisitedAdapter(this.getContext(), list);
        mRecyclerView.setAdapter(mAdapter);

        ref.child(user.getUid()+"/checkin").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot data, String s) {
                list.clear();
                final int size = list.size();
                final String photo = ""+data.child("img").getValue();
                final String title = ""+data.getKey();
//                String cnt = ;
                final String cate = ""+data.child("category").getValue();
                final String guName = ""+getGuName(Integer.parseInt(""+data.child("guNumber").getValue()));
                ref.addValueEventListener(new ValueEventListener() {
                    int chk_cnt = 0;
                    @Override
                    public void onDataChange(DataSnapshot cnt) {
                        for (DataSnapshot post: cnt.getChildren()) {
                            if(post.child("checkin") != null){
                                if(post.child("checkin").child(title).exists()){
                                    chk_cnt++;
                                }
                            }
                        }
                        list.add(size, new VisitedItem(photo, title, ""+chk_cnt, cate, guName));
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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


        return v;
    }
    public String getGuName(int guNumber){
        if (guNumber == 1 ){
            return "서대문·은평구";
        }else if(guNumber == 2){
            return "마포·용산구";
        }else if(guNumber == 3){
            return "중구·종로구";
        }else if(guNumber == 4){
            return "성북·동대문·성동구";
        }else if(guNumber == 5){
            return "강북·도봉·노원구";
        }else if(guNumber == 6){
            return "중량·광진구";
        }else if(guNumber == 7){
            return "송파·강동구";
        }else if(guNumber == 8){
            return "서초·강남구";
        }else if(guNumber == 9){
            return "관악·금천구";
        }else if(guNumber == 10){
            return "영등포·동작구";
        }else if(guNumber == 11){
            return "강서·양천·구로구";
        }else{
            return "";
        }
    }


    @Override
    public void onClick(View view) {

    }
}
