package com.example.workapp.frag_record;

import android.icu.text.SimpleDateFormat;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;


import com.example.workapp.R;
import com.example.workapp.db.AccountBean;
import com.example.workapp.db.DBManager;
import com.example.workapp.db.TypeBean;
import com.example.workapp.utils.BeiZhuDialog;
import com.example.workapp.utils.KeyBoardUtils;
import com.example.workapp.utils.SelectTimeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    KeyboardView keyboardView;
    EditText moneyEt; // 修改这里的类型为 EditText
    ImageView typeIv;
    TextView typeTv,beizhuTv,timeTv;
    GridView typeGv;
    List<TypeBean>typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_outcome,container,false);
        initView(view);
        setInitTime();
        setGvListener();
        loadDataToGv();
        return view;
    }

    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        String time = simpleDateFormat.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar =Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(mouth);
        accountBean.setDay(day);

    }

    private void  setGvListener(){
            typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                private TypeBean typeBean;

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.selectPos=position;
                    adapter.notifyDataSetChanged();
                    TypeBean typeBean = typeList.get(position);
                    String typename = typeBean.getTypename();
                    typeTv.setText(typename);
                    accountBean.setTypename(typename);
                    int simageId = typeBean.getSImagId();
                    typeIv.setImageResource(simageId);
                    accountBean.setsImageId(simageId);

                }
            });
        }



    public  void loadDataToGv() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);

    }

    private void initView(View view) {
        keyboardView=view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money); // 将 moneyEt 转换为 EditText
        typeIv=view.findViewById(R.id.frag_record_iv);
        typeGv=view.findViewById(R.id.frag_record_gv);
        typeTv=view.findViewById(R.id.frag_record_tv_type);
        beizhuTv=view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv=view.findViewById(R.id.frag_record_tv_time);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String money = moneyEt.getText().toString();
                if (TextUtils.isEmpty(money) || money.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float moneyFloat = Float.parseFloat(money);
                accountBean.setMoney(moneyFloat);
                saveAccountToDB();
                getActivity().finish();
            }
        })
    ;}

    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.frag_record_tv_time) {
           showTimeDialog();
        } else if (id == R.id.frag_record_tv_beizhu) {
            showBZDialog();
        }
    }

    private void showTimeDialog() {
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }


    /* 弹出备注对话框*/
    public  void showBZDialog(){
        BeiZhuDialog dialog = new BeiZhuDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BeiZhuDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = dialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    beizhuTv.setText(msg);
                    accountBean.setBeizhu(msg);
                }
                dialog.cancel();
            }
        });
    }
}
