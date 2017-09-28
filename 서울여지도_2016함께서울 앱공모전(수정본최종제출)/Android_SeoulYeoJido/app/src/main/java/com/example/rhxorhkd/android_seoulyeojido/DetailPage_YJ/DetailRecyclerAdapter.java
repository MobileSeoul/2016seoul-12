package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhxorhkd.android_seoulyeojido.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJ on 2016-10-25.
 */

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailRecyclerAdapter.ViewHolder>{

    private List<DetailReview> reviewList;
    private int itemLayout;


    /**
     * 생성자
     */
    public DetailRecyclerAdapter(List<DetailReview> items , int itemLayout){

        this.reviewList = items;
        this.itemLayout = itemLayout;
    }

    /**
     * 레이아웃을 만들어서 Holer에 저장
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        DetailReview item = reviewList.get(position);
        viewHolder.userName.setText(item.getTitle());
        viewHolder.reviewContent.setText(item.getReview());
        viewHolder.date.setText(item.getDate());
        viewHolder.img.setBackgroundResource(item.getImage());
        viewHolder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    /**
     * 뷰 재활용을 위한 viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView userName;
        public TextView reviewContent;
        public TextView date;
        public ViewHolder(View itemView){
            super(itemView);
            date =(TextView) itemView.findViewById(R.id.date);
            img = (ImageView) itemView.findViewById(R.id.imgProfile);
            userName = (TextView) itemView.findViewById(R.id.username);
            reviewContent = (TextView) itemView.findViewById(R.id.review);

        }

    }

    public void swap(ArrayList<DetailReview> datas){
        reviewList.clear();
        reviewList.addAll(datas);
        notifyDataSetChanged();
    }

}