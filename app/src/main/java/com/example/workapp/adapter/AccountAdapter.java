package com.example.workapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workapp.R;
import com.example.workapp.db.AccountBean;

import java.util.Calendar;
import java.util.List;

public class AccountAdapter extends BaseAdapter {
    // 成员变量
    Context context; // 上下文对象
    List<AccountBean> mDatas; // 存储AccountBean对象的列表
    LayoutInflater inflater; // LayoutInflater对象，用于加载布局
    int year, month, day; // 当前年份、月份和日期

    // 构造函数，初始化成员变量
    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context); // 从上下文中获取LayoutInflater实例

        Calendar calendar = Calendar.getInstance(); // 获取当前日期
        year = calendar.get(Calendar.YEAR); // 获取当前年份
        month = calendar.get(Calendar.MONTH) + 1; // 获取当前月份（注意月份是从0开始的，所以需要+1）
        day = calendar.get(Calendar.DAY_OF_MONTH); // 获取当前日期
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mainlv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AccountBean bean = mDatas.get(position);
        holder.typeIv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypename());
        holder.beizhuTv.setText(bean.getBeizhu());
        holder.moneyTv.setText(bean.getMoney() + "信用点");
        // 判断当前记录的日期是否与当前日期相同，如果相同则显示“今天”
        if (bean.getYear() == year && bean.getMonth() == month && bean.getDay() == day) {
            String time = bean.getTime().split(" ")[1]; // 获取时间部分（假设时间格式为"yyyy-MM-dd HH:mm:ss"）
            holder.timeTv.setText("今天 " + time); // 设置时间显示为“今天 HH:mm:ss”
        } else {
            holder.timeTv.setText(bean.getTime()); // 如果日期不同，则直接显示完整时间
        }
        return convertView;
    }
    class ViewHolder{
        ImageView typeIv;
        TextView typeTv,beizhuTv,timeTv,moneyTv;
        public ViewHolder(View view){
            typeIv = view.findViewById(R.id.item_mainlv_iv);
            typeTv = view.findViewById(R.id.item_mainlv_tv_title);
            timeTv = view.findViewById(R.id.item_mainlv_tv_time);
            beizhuTv = view.findViewById(R.id.item_mainlv_tv_beizhu);
            moneyTv = view.findViewById(R.id.item_mainlv_tv_money);

        }
    }
}
