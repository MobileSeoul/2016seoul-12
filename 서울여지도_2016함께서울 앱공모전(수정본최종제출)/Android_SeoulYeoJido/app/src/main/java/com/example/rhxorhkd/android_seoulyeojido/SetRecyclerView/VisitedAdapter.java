package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.DetailActivity;
import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.example.rhxorhkd.android_seoulyeojido.OtherSet;
import com.example.rhxorhkd.android_seoulyeojido.R;

import java.util.List;

/**
 * Created by 병윤 on 2016-10-24.
 */

public class VisitedAdapter extends RecyclerView.Adapter<VisitedViewHolder>{

    private Context mContext;
    private List<VisitedItem> list;

    public VisitedAdapter(Context context, List<VisitedItem> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public VisitedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visited_item, parent, false);
        return new VisitedViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(VisitedViewHolder holder, int position) {
        VisitedItem item = list.get(position);
        holder.cnt.setText(item.getCnt());
        holder.name.setText(item.getTitle());
        holder.cate_guName.setText(item.getCategory()+"·"+item.getGuName());
        Glide.with(mContext).load(item.getPhoto()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clickEvent(int position) {
        VisitedItem item = list.get(position);
        Intent i = new Intent(this.mContext, DetailActivity.class);
        i.putExtra("name", item.getTitle());
        mContext.startActivity(i);

    }
}
