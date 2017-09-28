package com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.example.rhxorhkd.android_seoulyeojido.R;
import com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView.AnalysisAdapter;
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
public class AnalysisFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public AnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analysis, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");
        user = auth.getCurrentUser();

        mRecyclerView = (RecyclerView)v.findViewById(R.id.analysis_rv);
        mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        final ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i<11; i++){
            list.add(i, 0);
        }

        mAdapter = new AnalysisAdapter(getContext(), list);
        mRecyclerView.setAdapter(mAdapter);



        ref.child(user.getUid()+"/checkin").addChildEventListener(new ChildEventListener() {

            int index;
            int chk_cnt;
            @Override
            public void onChildAdded(DataSnapshot data, String s) {

                if(data.child("guNumber") != null) {


                    index = Integer.parseInt("" + data.child("guNumber").getValue());
                    if(index == 1){
                        chk_cnt = list.get(8);
                        list.set(8, chk_cnt+1);
                    }
                    else if(index == 2){
                        chk_cnt = list.get(4);
                        list.set(4, chk_cnt+1);
                    }else if(index == 3){
                        chk_cnt = list.get(0);
                        list.set(0, chk_cnt+1);
                    }else if(index == 4){
                        chk_cnt = list.get(1);
                        list.set(1, chk_cnt+1);
                    }
                    else if(index == 5){
                        chk_cnt = list.get(10);
                        list.set(10, chk_cnt+1);
                    }
                    else if(index == 6){
                        chk_cnt = list.get(9);
                        list.set(9, chk_cnt+1);
                    }
                    else if(index == 7){
                        chk_cnt = list.get(3);
                        list.set(3, chk_cnt+1);
                    }
                    else if(index == 8){
                        chk_cnt = list.get(2);
                        list.set(2, chk_cnt+1);
                    }
                    else if(index == 9){
                        chk_cnt = list.get(5);
                        list.set(5, chk_cnt+1);
                    }
                    else if(index == 10){
                        chk_cnt = list.get(7);
                        list.set(7, chk_cnt+1);
                    }else if(index == 11){
                        chk_cnt = list.get(6);
                        list.set(6, chk_cnt+1);
                    }

                    mAdapter.notifyDataSetChanged();
                }
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

}
