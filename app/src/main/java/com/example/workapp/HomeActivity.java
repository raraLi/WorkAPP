package com.example.workapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workapp.adapter.AccountAdapter;
import com.example.workapp.db.AccountBean;
import com.example.workapp.db.DBManager;
import com.example.workapp.utils.BudgetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener { //定义ListView用于展示账户信息
    ListView todayLv;
    // 用于存储从数据库加载的账户数据
    List<AccountBean> mDatas;
    // 适配器，用于将数据与ListView绑定
    private AccountAdapter adapter;
    // 用于存储当前日期的年、月、日
    int year, month, day;
    // 顶部统计信息的TextView组件
    TextView topConTv, topOutTv, topInTv, topbudgetTv;
    // 用于切换显示/隐藏模式的ImageView
    ImageView topShowIv;
    // SharedPreferences用于存储预算信息
    SharedPreferences preferences;

    private TextView textHome, textRecord, textHistory, textInstall;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_home); // 设置Activity的布局文件
        initTime();// 初始化日期信息
        // 获取SharedPreferences实例
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        todayLv=findViewById(R.id.main_iv);
        addLVHeaderView();
        initView();
        mDatas= new ArrayList<>();
        adapter = new AccountAdapter(this,mDatas);
        todayLv.setAdapter(adapter);

        textHome = findViewById(R.id.text_home);
        textRecord = findViewById(R.id.text_record);
        textHistory = findViewById(R.id.text_history);
        textInstall = findViewById(R.id.text_install);
        textHome.setOnClickListener(this);
        textRecord.setOnClickListener(this);
        textHistory.setOnClickListener(this);
        textInstall.setOnClickListener(this);;
    }

    private void initView() {
        setLVLongClickListener();
    }   // 初始化视图组件，这里只设置了ListView的长按监听器
    private void setLVLongClickListener() { // 设置ListView的长按监听器，用于删除条目
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {  //点击了头布局
                    return false;
                }
                int pos = position-1;
                AccountBean clickBean = mDatas.get(pos);  //获取正在被点击的这条信息

                //弹出提示用户是否删除的对话框
                showDeleteItemDialog(clickBean);
                return false;
            }
        });
    }


    // 弹出删除对话框
    private void showDeleteItemDialog(final  AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("你要删啊щ(ºДºщ)")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int click_id = clickBean.getId();
                        //执行删除的操作
                        DBManager.deleteItemFromAccounttbById(click_id);
                        mDatas.remove(clickBean);
                        adapter.notifyDataSetChanged();
                        setTopTvShow();
                    }
                });
        builder.create().show();
    }


    // 为ListView添加头部视图
    private void addLVHeaderView() {
        // Inflate the layout for the header view
        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.item_mainlv_top, todayLv, false);

        // Find the views within the header layout
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);


        // Set click listeners for the views
        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);

        // Add the header view to the ListView
        todayLv.addHeaderView(headerView);
    }





    // 初始化日期信息
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // 在Activity恢复时加载数据库数据和更新顶部统计信息
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
        setTopTvShow();
    }

    // 更新顶部统计信息的显示
    private void setTopTvShow() {

        float incomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1);
        float outcomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0);
        String infoOneDay = "今日支出： "+outcomeOneDay+"信用点" + " 今日收入： "+incomeOneDay+"信用点";
        topConTv.setText(infoOneDay);
//
        float incomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);
        float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
        topInTv.setText(incomeOneMonth+"信用点");
        topOutTv.setText(outcomeOneMonth+"信用点");

        float bmoney = preferences.getFloat("bmoney",0);
        if (bmoney == 0) {
            topbudgetTv.setText("0 信用点");
        }else{
            float syMoney = bmoney-outcomeOneMonth;
            topbudgetTv.setText(syMoney+"信用点");
        }
    }

    // 从数据库加载数据并更新ListView
    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year,month,day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    // 处理底部导航文本视图的点击事件
    @Override
    public void onClick(View v) {
        Intent intent;
        int viewId = v.getId();

        if (viewId == R.id.text_home) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.text_record) {
            intent = new Intent(this, RecordActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.text_history) {
            intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.text_install) {
            showBudgetDialog();
        } else if (viewId == R.id.item_mainlv_top_iv_hide) {
            toggleShow();
        }
    }

    // 弹出设置预算的对话框
    private void showBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog(this);
        budgetDialog.show();
        budgetDialog.setDialogSize();
        budgetDialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("bmoney",money);
                editor.commit();
                float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
                float syMoney = money-outcomeOneMonth;//预算剩余
                topbudgetTv.setText(syMoney+"信用点");
            }
        });
    }

    boolean isShow= true;


    // 切换顶部统计信息的显示/隐藏模式

    private void toggleShow() {
        if (isShow) {
            PasswordTransformationMethod passwordMethod = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passwordMethod);   //设置隐藏
            topOutTv.setTransformationMethod(passwordMethod);   //设置隐藏
            topbudgetTv.setTransformationMethod(passwordMethod);   //设置隐藏
            topShowIv.setImageResource(R.mipmap.ih_hide);
            isShow = false;
        }else{
            HideReturnsTransformationMethod hideMethod = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideMethod);   //设置隐藏
            topOutTv.setTransformationMethod(hideMethod);   //设置隐藏
            topbudgetTv.setTransformationMethod(hideMethod);   //设置隐藏
            topShowIv.setImageResource(R.mipmap.ih_show);
            isShow = true;   //设置标志位为隐藏状态
        }
    }
    }
