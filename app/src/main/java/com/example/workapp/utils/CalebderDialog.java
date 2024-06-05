package com.example.workapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.workapp.R;
import com.example.workapp.adapter.CalebderAdapter;
import com.example.workapp.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalebderDialog extends Dialog implements View.OnClickListener {
    ImageView errorIv;
    GridView gv;
    LinearLayout hsvLayout ;
    List<TextView>hsvViewList;
    List<Integer>yearList;
    int selectPos=-1;
    int selectMonth=-1;
    private CalebderAdapter calebderAdapter;
    public interface OnRefreshListener{
        public void onRefresh(int selPos,int year,int month);
    }
    OnRefreshListener onRefreshListener;
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }


    public CalebderDialog(@NonNull Context context,int selectPos,int selectMonth) {
        super(context);
        this.selectPos =selectPos;
        this.selectMonth = selectMonth;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        gv = findViewById(R.id.dialog_calendar_gv);
        errorIv = findViewById((R.id.dialog_calendar_iv));
        hsvLayout = findViewById(R.id.dialog_calendar_layout);
        errorIv.setOnClickListener(this);
        addViewToLayout();
        initGridView();
        setGVLlistener();
    }

    private void setGVLlistener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calebderAdapter.selPos =position;
                calebderAdapter.notifyDataSetInvalidated();
                int mouth = position+1;
                int year = calebderAdapter.year;
                onRefreshListener.onRefresh(selectPos,year,mouth);
                cancel();

            }
        });

    }

    private void initGridView() {
        int selYear = yearList.get(selectPos);
        calebderAdapter = new CalebderAdapter(getContext(), selYear);
        if (selectMonth == -1) {
            int month = Calendar.getInstance().get(Calendar.MONTH);
            calebderAdapter.selPos = month;
        }else {
            calebderAdapter.selPos = selectMonth-1;
        }
        gv.setAdapter(calebderAdapter);
    }

    private void  addViewToLayout(){
        hsvViewList= new ArrayList<>();
        yearList = DBManager.getYearListFromAccounttb();
    if (yearList.size() == 0) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        yearList.add(year);
    }
    for (int i = 0; i < yearList.size(); i++) {
        int year = yearList.get(i);
        View view = getLayoutInflater().inflate(R.layout.item_dialogcal_hsv, null);
        hsvLayout.addView(view);
        TextView hsvTv = view.findViewById(R.id.item_dialogcal_hsv_tv);
        hsvTv.setText(year+"");
        hsvViewList.add(hsvTv);
    }
    if (selectPos == -1) {
        selectPos = hsvViewList.size()-1;
    }
    setHSVClickListener(); //设置监听
}

    private void setHSVClickListener() {
        for (int i = 0; i < hsvViewList.size(); i++) {
            TextView view = hsvViewList.get(i);
            final int pos = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPos = pos;
                    int year = yearList.get(selectPos);
                    calebderAdapter.setYear(year);
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_calendar_iv) {
            cancel();
        }
    }
    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}


