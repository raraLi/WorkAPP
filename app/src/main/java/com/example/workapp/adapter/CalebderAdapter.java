package com.example.workapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.workapp.R;

import java.util.ArrayList;
import java.util.List;

public class CalebderAdapter extends BaseAdapter {
    Context context;
    List<String> mDtas;
    public  int year;
    public int selPos = -1;
    public void setYear(int year){
        this.year = year;
        mDtas.clear();
        loadDatas(year);
        notifyDataSetChanged();
    }

    public CalebderAdapter(Context context, int year) {
        this.context = context;
        this.year = year;
        mDtas=new ArrayList<>();
        loadDatas(year);

    }

    private void loadDatas(int year) {
        for(int i=1;i<13;i++){
            String data = year +"/"+i;
            mDtas.add(data);


        }
    }

    @Override
    public int getCount() {
        return mDtas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDtas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialogcal_gv,parent,false);
        TextView tv = convertView.findViewById(R.id.item_dialogcal_gv_tv);
        tv.setText(mDtas.get(position));
        tv.setBackgroundResource(R.color.grey);
        tv.setTextColor(Color.BLACK);
        if (position == selPos) {
            tv.setBackgroundResource(R.color.wheat);
            tv.setTextColor(Color.WHITE);
        }
        return convertView;
    }
}
