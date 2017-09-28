package com.example.rhxorhkd.android_seoulyeojido.SetActivityFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rhxorhkd.android_seoulyeojido.R;
import com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView.AnalysisAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OtherAnalysisFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private FirebaseDatabase db;
    private DatabaseReference ref;

    public OtherAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analysis, container, false);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("member");

        mRecyclerView = (RecyclerView)v.findViewById(R.id.analysis_rv);
        mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i<11; i++){
            list.add(i, 0);
        }

        mAdapter = new AnalysisAdapter(getContext(), list);
        mRecyclerView.setAdapter(mAdapter);

        Intent i = getActivity().getIntent();
        String uid = i.getStringExtra("uid");

        ref.child(uid+"/checkin").addChildEventListener(new ChildEventListener() {

            int index;
            int chk_cnt;
            @Override
            public void onChildAdded(DataSnapshot data, String s) {


                if(data.child("guNumber") != null) {
                    index = Integer.parseInt("" + data.child("guNumber").getValue())-1;
                    chk_cnt = list.get(index);
                    list.set(index, chk_cnt+1);
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
