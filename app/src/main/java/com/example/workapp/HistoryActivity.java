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
    ListView historyLv;
    TextView timeTv;
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month;
    int dialogSelPos = -1;
    int dialogSelMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        historyLv=findViewById(R.id.history_lv);
        timeTv=findViewById(R.id.history_tv_time);
        mDatas = new ArrayList<>();
        // 设置适配器
        adapter = new AccountAdapter(this,mDatas);
        historyLv.setAdapter(adapter);
        initTime();
        timeTv.setText(year+"年"+month+"月");
        loadData(year,month);
        setLVClickListener();

    }

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

    private void deleteItem(AccountBean accountBean) {
        final int delId = accountBean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("你要删啊щ(ºДºщ)")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteItemFromAccounttbById(delId);
                        mDatas.remove(accountBean);   //实时刷新，从数据源删除
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();
    }

    private void loadData(int year, int month) {
        // 假设默认日期为每个月的第一天
        int day = 1;
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }


    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }

    public void onClick(View view){
            if (view.getId() == R.id.history_iv_back) {
                finish();
            } else if (view.getId() == R.id.history_iv_rili) {
                CalebderDialog dialog = new CalebderDialog(this,this.dialogSelPos,dialogSelMonth);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener(new CalebderDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selPos, int year, int month) {
                        timeTv.setText(year + "年" + month + "月");
                        loadData(year, month);
                        dialogSelPos = selPos;
                        dialogSelMonth = month;
                    }
                });
            }

        }
}