package com.example.rhxorhkd.android_seoulyeojido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhxorhkd on 2016-10-17.
 */

public class SearchitemAdapter extends BaseAdapter{

    LayoutInflater inflater;
    private ArrayList<Searchitem> data;
    private List<Searchitem> searchlist;
    private int layout;

    public SearchitemAdapter(Context context, int layout,List<Searchitem> searchlist){
        this.inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.searchlist = searchlist;
        this.data = new ArrayList<Searchitem>();
        this.data.addAll(searchlist);
        this.layout=layout;
    }


    @Override
    public int getCount() {
        return searchlist.size();
    }

    @Override
    public Object getItem(int position) {
        String str;
        str=searchlist.get(position).getName()+":"+searchlist.get(position).getState();
        return str;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = inflater.inflate(layout,parent,false);

        Searchitem searchitem =searchlist.get(position);

        TextView textview = (TextView)convertView.findViewById(R.id.searchname);
        textview.setText(searchitem.getName());

        ImageView icon = (ImageView)convertView.findViewById(R.id.searchicon);
        icon.setImageResource(searchitem.getIcon());

        return convertView;
    }

    public void filter(String charText){
       // Log.d("text",""+charText.length());

        searchlist.clear();
        if(charText.length()==0){
            adddata();
        }else{
            for(Searchitem wp : data){
               // Log.d("text","-->"+wp.getName().contains(charText));
                if(wp.getName().contains(charText)){
                   // Log.d("text","------>"+wp.getName());
                    searchlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void adddata(){
        Searchitem searchitem1 = new Searchitem("",R.drawable.white,0);
        Searchitem searchitem2 = new Searchitem("",R.drawable.white,0);
        Searchitem searchitem3 = new Searchitem("",R.drawable.white,0);
        Searchitem searchitem4 = new Searchitem("",R.drawable.white,0);
        Searchitem searchitem5 = new Searchitem("",R.drawable.white,0);
        Searchitem searchitem6 = new Searchitem("",R.drawable.white,0);
        Searchitem searchitem7 = new Searchitem("",R.drawable.white,0);

        searchlist.add(searchitem1);
        searchlist.add(searchitem2);
        searchlist.add(searchitem3);
        searchlist.add(searchitem4);
        searchlist.add(searchitem5);
        searchlist.add(searchitem6);
        searchlist.add(searchitem7);
    }
}
