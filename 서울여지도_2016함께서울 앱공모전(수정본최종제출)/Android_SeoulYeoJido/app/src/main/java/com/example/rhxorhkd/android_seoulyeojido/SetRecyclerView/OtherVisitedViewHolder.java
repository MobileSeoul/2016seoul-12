package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ.DetailActivity;
import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.example.rhxorhkd.android_seoulyeojido.R;

/**
 * Created by 병윤 on 2016-10-30.
 */

public class OtherVisitedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    final View mView;
    final ImageView photo;
    final TextView name;
    final TextView cnt, cate_guName;

    private OtherVisitedAdapter otherVisitedAdapter;

    public OtherVisitedViewHolder(View itemView, OtherVisitedAdapter otherVisitedAdapter) {
        super(itemView);
        mView = itemView;
        photo = (ImageView)itemView.findViewById(R.id.other_visited_img);
        name = (TextView)itemView.findViewById(R.id.other_visited_name);
        cnt = (TextView)itemView.findViewById(R.id.other_visited_cnt);
        cate_guName = (TextView)itemView.findViewById(R.id.other_visited_cate_gu);

        this.otherVisitedAdapter = otherVisitedAdapter;
        RelativeLayout rl = (RelativeLayout)itemView.findViewById(R.id.other_visited_rl);
        rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        switch (view.getId()){
            case R.id.other_visited_rl :
                otherVisitedAdapter.clickEvent(position);
                break;
            default: break;
        }
    }

}
