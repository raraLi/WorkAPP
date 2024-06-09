package com.example.workapp.frag_record;
import androidx.fragment.app.Fragment;
import com.example.workapp.R;
import com.example.workapp.db.DBManager;
import com.example.workapp.db.TypeBean;

import java.util.List;

public class OutcomeFragment extends  BaseFragment {

    // 重写loadDataToGv方法，用于加载支出相关的类型数据到GridView
    @Override
    public void loadDataToGv() {
        super.loadDataToGv(); // 调用父类的loadDataToGv方法，进行基础设置

        // 从数据库中获取支出类型的列表
        List<TypeBean> outlist = DBManager.getTypeList(0); // 假设0代表支出类型

        // 将获取到的支出类型列表添加到GridView的数据源中
        typeList.addAll(outlist);

        // 通知GridView适配器数据已经改变，需要刷新显示
        adapter.notifyDataSetChanged();

        // 设置默认的支出类型名称和图标
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.ic_qita_fs); // 这里使用的是“其他”的默认图标，可能需要更换为支出类型的默认图标
    }

    // 重写saveAccountToDB方法，用于将支出记录保存到数据库
    @Override
    public void saveAccountToDB() {
        // 设置账户类型为支出（假设0代表支出）
        accountBean.setKind(0);

        // 将账户数据插入到数据库中
        DBManager.insertItemToAccounttb(accountBean);
    }
}