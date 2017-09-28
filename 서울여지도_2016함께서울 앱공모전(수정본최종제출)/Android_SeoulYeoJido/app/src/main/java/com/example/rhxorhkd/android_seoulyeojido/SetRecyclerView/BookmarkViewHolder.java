package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhxorhkd.android_seoulyeojido.R;

/**
 * Created by 병윤 on 2016-10-25.
 */

public class BookmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    final View mView;
    final ImageView photo;
    final TextView name;
    final TextView cnt;

    private BookmarkAdapter bookmarkAdapter;

    public BookmarkViewHolder(View itemView, BookmarkAdapter bookmarkAdapter) {
        super(itemView);
        mView = itemView;
        photo = (ImageView)itemView.findViewById(R.id.visited_img);
        name = (TextView)itemView.findViewById(R.id.visited_name);
        cnt = (TextView)itemView.findViewById(R.id.visited_cnt);
    }

    @Override
    public void onClick(View view) {

    }
}
