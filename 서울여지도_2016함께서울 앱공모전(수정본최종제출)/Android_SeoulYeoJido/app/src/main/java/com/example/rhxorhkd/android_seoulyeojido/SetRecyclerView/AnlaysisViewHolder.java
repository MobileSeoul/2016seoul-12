package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhxorhkd.android_seoulyeojido.R;

/**
 * Created by 병윤 on 2016-10-29.
 */

public class AnlaysisViewHolder extends RecyclerView.ViewHolder {

    final View mView;
    final ImageView gu_img;
    final TextView gu_name;
    final TextView percent;

    public AnlaysisViewHolder(View itemView, AnalysisAdapter analysisAdapter) {
        super(itemView);
        mView = itemView;
        gu_img = (ImageView)itemView.findViewById(R.id.analysis_img);
        gu_name = (TextView)itemView.findViewById(R.id.analysis_location_name);
        percent = (TextView)itemView.findViewById(R.id.analysis_percent);

    }
}
