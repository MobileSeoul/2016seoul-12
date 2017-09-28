package com.example.rhxorhkd.android_seoulyeojido.SetRecyclerView;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.Model.VisitedItem;
import com.example.rhxorhkd.android_seoulyeojido.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 병윤 on 2016-10-29.
 */

public class AnalysisAdapter extends RecyclerView.Adapter<AnlaysisViewHolder> {

    private Context mContext;
    private List<Integer> list;

    private TextView gu_name, percent;

    public AnalysisAdapter(Context mContext, List<Integer> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public AnlaysisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.analysis_item, parent, false);
        return new AnlaysisViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(AnlaysisViewHolder holder, int position) {
        int chk_cnt = list.get(position);
        int photo;
        switch (position){
            case 0 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =351;

                holder.gu_name.setText("중구.종로");
                holder.percent.setText(""+getPercent(chk_cnt, 21)+"%");
                if(getImgNum(getPercent(chk_cnt, 21)) == 1){
                    photo = R.drawable.three_1;
                }else if( getImgNum(getPercent(chk_cnt, 21)) == 2 ){
                    photo =R.drawable.three_2;
                }else if(getImgNum(getPercent(chk_cnt, 21)) == 3){
                    photo =R.drawable.three_3;
                }else if(getImgNum(getPercent(chk_cnt, 21)) == 4){
                    photo =R.drawable.three_4;
                }else{
                    photo =R.drawable.three_5;
                }
                Glide
                    .with(mContext)
                    .load(photo)
                    .into(holder.gu_img);
                break;
            case 1 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =351;

                holder.gu_name.setText("성북.동대문.성동");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.four_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.four_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.four_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.four_4;
                }else{
                    photo =R.drawable.four_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);
                break;


            case 2 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =294;


                holder.gu_name.setText("서초.강남");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.eight_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.eight_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.eight_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.eight_4;
                }else{
                    photo =R.drawable.eight_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);
                break;
            case 3 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =294;


                holder.gu_name.setText("송파.강동");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.seven_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.seven_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.seven_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.seven_4;
                }else{
                    photo =R.drawable.seven_5;
                }
                Glide
                    .with(mContext)
                    .load(photo)
                    .into(holder.gu_img);
                break;

            case 4 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =270;

                holder.gu_name.setText("마포.용산");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.two_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.two_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.two_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.two_4;
                }else{
                    photo =R.drawable.two_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);
                break;


            case 5 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =270;

                holder.gu_name.setText("관악.금천");

                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.nine_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.nine_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.nine_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.nine_4;
                }else{
                    photo =R.drawable.nine_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);
                break;



            case 6 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =255;

                holder.gu_name.setText("강서.양천.구로");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.eleven_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.eleven_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.eleven_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.eleven_4;
                }else{
                    photo =R.drawable.eleven_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);

                break;
            case 7 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =255;

                holder.gu_name.setText("영등포.동작");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.ten_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.ten_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.ten_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.ten_4;
                }else{
                    photo =R.drawable.ten_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);

                break;

            case 8 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =282;

                holder.gu_name.setText("서대문.은평");

                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.one_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.one_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.one_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.one_4;
                }else{
                    photo =R.drawable.one_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);

                break;

            case 9 :
                holder.gu_img.getLayoutParams().width =420;
                holder.gu_img.getLayoutParams().height =282;


                holder.gu_name.setText("중랑.광진");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.six_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.six_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.six_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.six_4;
                }else{
                    photo =R.drawable.six_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);

                break;

            case 10 :
                holder.gu_img.getLayoutParams().width = 420;
                holder.gu_img.getLayoutParams().height = 204;

                holder.gu_name.setText("강동.도봉.노원");
                holder.percent.setText(""+getPercent(chk_cnt, 5)+"%");

                if(getImgNum(getPercent(chk_cnt, 5)) == 1){
                    photo = R.drawable.five_1;
                }else if( getImgNum(getPercent(chk_cnt, 5)) == 2 ){
                    photo =R.drawable.five_2;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 3){
                    photo =R.drawable.five_3;
                }else if(getImgNum(getPercent(chk_cnt, 5)) == 4){
                    photo =R.drawable.five_4;
                }else{
                    photo =R.drawable.five_5;
                }
                Glide
                        .with(mContext)
                        .load(photo)
                        .into(holder.gu_img);

                break;

            default:break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getPercent(double chk, double total){
         double percent = (chk/total)*100.0;
        return (int)percent;

    }

    public int getImgNum(int percent){
        if(percent == 0)
            return 1;
        else if(percent>0 && percent <=33)
            return 2;
        else if(percent>33 && percent <=66)
            return 3;
        else if(percent>66 && percent <=99)
            return 4;
        else if(percent==100)
            return 5;
        else
            return 1;
    }
}
