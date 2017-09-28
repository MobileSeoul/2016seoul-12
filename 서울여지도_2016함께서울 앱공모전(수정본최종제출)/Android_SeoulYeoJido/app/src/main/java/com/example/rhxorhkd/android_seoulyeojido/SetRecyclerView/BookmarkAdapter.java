package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.example.rhxorhkd.android_seoulyeojido.R;

import java.util.List;

/**
 * Created by 병윤 on 2016-10-25.
 */

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {

    private Context mContext;
    private List<VisitedItem> list;

    public BookmarkAdapter(Context mContext, List<VisitedItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visited_item, parent, false);
        return new BookmarkViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(BookmarkViewHolder holder, int position) {
        VisitedItem item = list.get(position);
//        holder.photo.setImageURI();
        holder.cnt.setText(item.getCnt());
//        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
