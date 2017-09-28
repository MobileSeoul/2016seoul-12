package com.example.rhxorhkd.android_seoulyeojido.DetailPage_YJ;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rhxorhkd.android_seoulyeojido.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hanyoojin on 2016. 10. 25..
 */

public class ImageGridAdapter extends BaseAdapter {
    Context context;
    String images;
    JSONObject object;
    JSONArray array;
    public ArrayList<String> mThumblds = new ArrayList<>();

    public ImageGridAdapter(Context context,String images){
        this.context = context;
        this.images =images;
        try {
            object = new JSONObject(images);
            array = object.getJSONArray("loca_photo");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(array!=null){
            for(int i=0;i<array.length();i++){
                try {
                    String temp = array.get(i).toString();
                    mThumblds.add(temp);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public int getCount() {
        return mThumblds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300,300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imageView = (ImageView) convertView;
        }


            Glide.with(context).load( mThumblds.get(position)).into(imageView);

        //imageView.setImageResource(mThumblds[position]);
        return imageView;
    }
}

