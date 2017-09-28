package com.example.rhxorhkd.android_seoulyeojido.RankRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.rhxorhkd.android_seoulyeojido.MainActivity;
import com.example.rhxorhkd.android_seoulyeojido.Model.RankItem;
import com.example.rhxorhkd.android_seoulyeojido.OtherSet;
import com.example.rhxorhkd.android_seoulyeojido.R;
import com.example.rhxorhkd.android_seoulyeojido.rank;
import com.example.rhxorhkd.android_seoulyeojido.set;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by 병윤 on 2016-10-24.
 */

public class RankAdapter extends RecyclerView.Adapter<RankViewHolder>{

    private FirebaseAuth auth;
    private Context mContext;
    private List<RankItem> list;

    public RankAdapter(Context context, List<RankItem> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rank_first_item, parent, false);
        return new RankViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(final RankViewHolder holder, int position) {
        RankItem item = list.get(position);
        holder.visited_cnt.setText(item.getChk_cnt());


        if(position == 0){
            holder.profile.setBackgroundResource(R.drawable.rank_item_border);
            holder.upline.setVisibility(View.VISIBLE);
            holder.upline.setBackgroundColor(0xFF000000);
            holder.downline.setVisibility(View.VISIBLE);
            holder.downline.setBackgroundColor(0xFF000000);
            holder.visited_cnt.setTextColor(0xFF3f51b5);
            Glide.with(mContext).load(R.drawable.rank_one).into(holder.medal);
        }else if(position == 1){
            holder.profile.setBackgroundResource(R.drawable.rank_item_border);
            holder.downline.setBackgroundColor(0xFFd7d7d7);
            holder.upline.setVisibility(View.GONE);
            Glide.with(mContext).load(R.drawable.rank_two).into(holder.medal);
        }else if(position == 2){
            holder.profile.setBackgroundResource(R.drawable.rank_item_border);
            holder.downline.setBackgroundColor(0xFFd7d7d7);
            holder.upline.setVisibility(View.GONE);
            Glide.with(mContext).load(R.drawable.rank_three).into(holder.medal);
        }else{
            Glide.with(mContext).load("").into(holder.medal);
            holder.profile.setBackgroundColor(0);
            holder.downline.setBackgroundColor(0xFFd7d7d7);
            holder.upline.setVisibility(View.GONE);
        }
        holder.rank.setText(""+(position+1));
        holder.nickname.setText(item.getNickname());
//        holder.nickname.setText("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        holder.nickname.setLines(1);

        Glide.with(mContext).load(item.getImg()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.profile){
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.profile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clickEvent(int position) {
        RankItem item = list.get(position);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user.getUid().toString().equals(list.get(position).getEct())){
            if(mContext instanceof rank){
                (((rank) mContext)).goMypage();
            }

        }else{
            Intent i = new Intent(this.mContext, OtherSet.class);
            i.putExtra("name", item.getNickname());
            i.putExtra("profile", item.getImg());
            i.putExtra("uid", item.getEct());
            i.putExtra("cnt", item.getChk_cnt());
            this.mContext.startActivity(i);
        }

    }
}
