package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhxorhkd.android_seoulyeojido.R;

/**
 * Created by 병윤 on 2016-10-24.
 */

public class VisitedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    final View mView;
    final ImageView photo;
    final TextView name;
    final TextView cnt, cate_guName;

    private VisitedAdapter visitedAdapter;

    public VisitedViewHolder(View itemView, VisitedAdapter visitedAdapter) {
        super(itemView);
        mView = itemView;
        photo = (ImageView)itemView.findViewById(R.id.visited_img);
        name = (TextView)itemView.findViewById(R.id.visited_name);
        cnt = (TextView)itemView.findViewById(R.id.visited_cnt);
        cate_guName = (TextView)itemView.findViewById(R.id.visited_cate_gu);

        this.visitedAdapter = visitedAdapter;

        RelativeLayout rl = (RelativeLayout) itemView.findViewById(R.id.visited_rl);
        rl.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        switch (view.getId()){
            case R.id.visited_rl :
                visitedAdapter.clickEvent(position);
                break;
            default: break;
        }

    }
}
