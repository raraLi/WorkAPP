package com.example.workapp.frag_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workapp.R;
import com.example.workapp.db.TypeBean;
import com.example.workapp.utils.ResourceUtil;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {
    // 成员变量
    Context context; // 上下文对象，用于访问应用环境和资源
    List<TypeBean> mDatas; // 存储TypeBean对象的列表，作为数据源
    int selectPos = 0; // 当前选中的位置，默认为0

    // 构造函数，用于初始化适配器
    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    // 获取数据源中的项数
    @Override
    public int getCount() {
        return mDatas.size();
    }

    // 根据位置获取数据源中的项
    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    // 获取指定位置的项的唯一标识符
    @Override
    public long getItemId(int position) {
        return position; // 这里直接使用位置作为唯一标识符，但通常建议使用更稳定的唯一ID
    }

    // 获取指定位置的视图，用于在GridView中展示
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 加载新的视图
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, parent, false);

        // 从convertView中查找ImageView和TextView组件
        ImageView iv = convertView.findViewById(R.id.item_record_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);

        // 从数据源中获取TypeBean对象
        TypeBean typeBean = mDatas.get(position);

        // 设置TextView的文本为TypeBean的类型名称
        tv.setText(typeBean.getTypename());

        // 根据是否选中来设置ImageView的图片资源
        if (selectPos == position) {
            iv.setImageResource(ResourceUtil.getSImage(typeBean.getSImagId())); // 选中时显示特定的图片资源（可能是选中状态的图标）
        } else {
            iv.setImageResource(ResourceUtil.getImage(typeBean.getImageId())); // 未选中时显示默认的图片资源
        }
        // 返回加载或复用的视图
        return convertView;
    }
}

