package com.example.rhxorhkd.android_seoulyeojido.RankRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rhxorhkd.android_seoulyeojido.R;

/**
 * Created by 병윤 on 2016-10-24.
 */

public class RankViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    final View mView;
    final TextView rank, visited_cnt;
    final ImageView profile, medal;
    final TextView nickname;
    final View upline, downline;
    private RankAdapter rankAdapter;



    public RankViewHolder(View itemView, RankAdapter rankAdapter) {
        super(itemView);
        mView = itemView;
        medal = (ImageView)itemView.findViewById(R.id.medal);
        upline = (View) itemView.findViewById(R.id.view_up_line);
        downline = (View)itemView.findViewById(R.id.view_down_line);

        visited_cnt = (TextView)itemView.findViewById(R.id.visited_cnt);
        rank = (TextView) itemView.findViewById(R.id.rank_order);
        nickname = (TextView)itemView.findViewById(R.id.plain_username);
        profile = (ImageView)itemView.findViewById(R.id.first_profile_img);


        RelativeLayout rl = (RelativeLayout) itemView.findViewById(R.id.rank_list);
        rl.setOnClickListener(this);

        this.rankAdapter = rankAdapter;
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        switch (view.getId()){
            case R.id.rank_list :

                rankAdapter.clickEvent(position);
                break;
            default: break;
        }
    }
}