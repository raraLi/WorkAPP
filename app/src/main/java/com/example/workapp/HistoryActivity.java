package com.example.workapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workapp.adapter.AccountAdapter;
import com.example.workapp.db.AccountBean;
import com.example.workapp.db.DBManager;
import com.example.workapp.utils.CalebderDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    // ListView用于展示账户历史记录
    ListView historyLv;
    // TextView用于显示当前选择的年份和月份
    TextView timeTv;
    // 存储从数据库加载的账户数据列表
    List<AccountBean> mDatas;
    // 适配器，用于将账户数据与ListView绑定
    AccountAdapter adapter;
    // 当前年份和月份
    int year, month;
    // 用于存储日历对话框中用户选择的位置和月份，但初始化为-1表示未选择
    int dialogSelPos = -1;
    int dialogSelMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置活动的全屏显示
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);//设置活动的布局文件
        //初始化视图组件
        historyLv=findViewById(R.id.history_lv);
        timeTv=findViewById(R.id.history_tv_time);
        mDatas = new ArrayList<>();//初始化数据列表


        // 创建适配器并设置给ListView
        adapter = new AccountAdapter(this,mDatas);
        historyLv.setAdapter(adapter);
         // 初始化当前日期
        initTime();
        // 设置时间TextView的显示内容
        timeTv.setText(year + "年" + month + "月");

        // 加载当前年份和月份的数据到ListView
        loadData(year,month);

        // 设置ListView的项点击监听器（注意：此处只设置了长按监听器，没有设置普通点击监听器）
        setLVClickListener();

    }

    // 设置ListView的长按监听器，用于删除条目
    private void setLVClickListener() {
        historyLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBean accountBean = mDatas.get(position);
                deleteItem(accountBean);
                return false;
            }
        });
    }


    // 删除指定账户条目，并弹出确认对话框
    private void deleteItem(AccountBean accountBean) {
        // 获取要删除的条目ID
        final int delId = accountBean.getId();
        // 创建并显示确认删除对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("这里是提示").setMessage("你要删啊щ(ºДºщ)")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 从数据库中删除条目
                        DBManager.deleteItemFromAccounttbById(delId);
                        mDatas.remove(accountBean);
                        // 刷新ListView的显示
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show(); // 显示对话框
    }

    // 从数据库中加载指定年份和月份的数据到ListView
    private void loadData(int year, int month) {
        List<AccountBean> list = DBManager.getAccountListOneMonthFromAccounttb(year, month);
        mDatas.clear();// 清空原有数据
        mDatas.addAll(list);// 添加新加载的数据
        adapter.notifyDataSetChanged(); // 刷新ListView的显示
    }

    // 初始化当前日期信息
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);// 获取当前年份
        month = calendar.get(Calendar.MONTH)+1;// 获取当前月份（注意：月份是从0开始的，所以需要+1）
    }

    //处理视图点击事件
    public void onClick(View view){
        // 根据点击的视图ID执行不同的操作
            if (view.getId() == R.id.history_iv_back) {
                finish();
                // 如果点击了返回按钮，则结束当前活动
            } else if (view.getId() == R.id.history_iv_rili) {
                CalebderDialog dialog = new CalebderDialog(this,this.dialogSelPos,dialogSelMonth);
                // 如果点击了日历按钮，则显示自定义的日历对话框
                dialog.show();
                dialog.setDialogSize();//设置对话框大小（具体实现未在代码段中给出）
                // 设置对话框刷新监听器，当用户在对话框中选择新的日期时，刷新时间和数据列表
                dialog.setOnRefreshListener(new CalebderDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selPos, int year, int month) {
                        timeTv.setText(year + "年" + month + "月");// 更新时间TextView的显示内容
                        loadData(year, month);// 加载新选择的日期对应的数据到ListView
                        dialogSelPos = selPos;// 更新选择的位置
                        dialogSelMonth = month;// 更新选择的月份
                    }
                });
            }

        }
}