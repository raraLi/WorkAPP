package com.example.workapp.frag_record;

import android.icu.text.SimpleDateFormat;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.example.workapp.utils.ResourceUtil;
import com.example.workapp.utils.SelectTimeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    // UI组件
    EditText moneyEt; // 输入金额的EditText
    ImageView typeIv; // 显示类型图标的ImageView
    TextView typeTv, beizhuTv, timeTv; // 显示类型名称、备注和时间的TextView
    GridView typeGv; // 显示类型列表的GridView

    // 数据列表和适配器
    List<TypeBean> typeList; // 类型数据列表
    TypeBaseAdapter adapter; // GridView的适配器

    // 账户数据模型
    AccountBean accountBean; // 账户数据Bean

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化账户Bean，并设置默认类型为“其他”
        accountBean = new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载布局并初始化视图组件
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view); // 初始化视图组件
        setInitTime(); // 设置初始时间
        loadDataToGv(); // 加载类型数据到GridView
        setGvListener(); // 设置GridView的点击监听器
        return view; // 返回加载的视图
    }

    // 设置初始时间并更新UI和账户Bean
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        String time = simpleDateFormat.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);
//当用户点击GridView中的某个类型时，更新UI和账户Bean）
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(mouth);
        accountBean.setDay(day);

    }

    private void setGvListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();
                TypeBean typeBean = typeList.get(position);
                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int simageId = ResourceUtil.getSImage(typeBean.getSImagId());
                typeIv.setImageResource(simageId);
                accountBean.setsImageId(simageId);
            }
        });
    }

    // 加载类型数据到GridView
    public void loadDataToGv() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);


    }

    // 初始化视图组件并设置监听器
    private void initView(View view) {
        moneyEt = view.findViewById(R.id.frag_record_et_money); // 将 moneyEt 转换为 EditText
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv = view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        // 为moneyEt设置编辑完成监听器，当用户完成输入金额后，保存账户数据并关闭Activity
        moneyEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String money = moneyEt.getText().toString();
                    if (TextUtils.isEmpty(money) || money.equals("0")) {
                        getActivity().finish();
                        return true;
                    }
                    float moneyFloat = Float.parseFloat(money);
                    accountBean.setMoney(moneyFloat);
                    saveAccountToDB();
                    getActivity().finish();


                    return true;
                }

                return false;
            }
        });

    }

    // 抽象方法，用于在子类中实现保存账户数据到数据库的逻辑
    public abstract void saveAccountToDB();


    // 实现View.OnClickListener接口中的onClick方法，处理点击事件
    @Override
    public void onClick(View v) {
        // 根据点击的视图ID执行不同的操作，如显示时间选择对话框或备注对话框
        int id = v.getId();
        if (id == R.id.frag_record_tv_time) {
            showTimeDialog();
        } else if (id == R.id.frag_record_tv_beizhu) {
            showBZDialog();
        }
    }

    // 显示时间选择对话框，并设置监听器以更新UI和账户Bean
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



    // 显示备注对话框，并设置监听器以更新备注TextView和账户Bean
    public void showBZDialog() {
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
