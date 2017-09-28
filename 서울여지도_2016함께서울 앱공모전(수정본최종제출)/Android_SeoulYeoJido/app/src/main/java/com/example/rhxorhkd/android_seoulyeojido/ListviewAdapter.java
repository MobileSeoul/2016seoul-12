package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by rhxorhkd on 2016-10-15.
 */

public class ListviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Listviewitem> data;
    private int layout;
    private View tempview;
    public static int counts=0;
    public Context context;




    public ListviewAdapter(Context context, int layout, ArrayList<Listviewitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
        this.context=context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        //체크박스 상태를 변경

        if(convertView==null)
            convertView = inflater.inflate(layout,parent,false);

        tempview = convertView;
        Listviewitem listviewitem = data.get(position);
        ImageView icon = (ImageView) convertView.findViewById(R.id.lay_imageview);

        setImagedata setImagedata = new setImagedata(icon);
        setImagedata.execute(listviewitem.getIcon());

        TextView name = (TextView)convertView.findViewById(R.id.lay_textview);
        name.setText(listviewitem.getName());

        TextView name2 = (TextView)convertView.findViewById(R.id.lay_textcheckcount); //checkin 카운트
        name2.setText(listviewitem.getSubname());


        TextView name3 = (TextView)convertView.findViewById(R.id.lay_textview3);  //카테고리
        name3.setText(listviewitem.getThirdname());

        TextView name4 = (TextView)convertView.findViewById(R.id.lay_textview4); // 장소
        name4.setText(listviewitem.getFourthname());






//        hearticon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Log.d("list","posi ->"+position+b);
//                String result = ""; // 문자열 초기화는 빈문자열로 하자
//
//                if(checked[position]){
//                    checked[position]=false;
//                    Log.d("list","취소"+position);
//                    hearticon.setChecked(false);
//                }else{
//                    checked[position]=true;
//                    Log.d("list","선택"+position);
//                    hearticon.setChecked(true);
//                }
//              /*  if(hearticon.isChecked()) {
//                    hearticon.setChecked(true);
//                   Log.d("checkcheck", "ok@");
//                }
//                if(!hearticon.isChecked()) {
//                    Log.d("checkcheck", "no@");
//                }*/
//               // notifyDataSetChanged();
//            }
//        });











        Log.d("layout","position : "+position);
        Log.d("layout",""+listviewitem.getName());
        return convertView;
    }


    public class setImagedata extends AsyncTask<String, Void, String>{
        public  ImageView icon;
        public setImagedata(ImageView icon){
            this.icon=icon;
        }
        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }
        @Override
        protected void onPostExecute(String s) {
            Glide.with(context).load(s).into(icon);
        }
    }
}
