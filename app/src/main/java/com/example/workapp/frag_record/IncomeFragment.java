package com.example.workapp.frag_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.workapp.R;
import com.example.workapp.db.DBManager;
import com.example.workapp.db.TypeBean;

import java.util.List;

/**
 *
 * create an instance of this fragment.
 */
public class IncomeFragment extends BaseFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv(); // 调用父类的loadDataToGv方法，初始化GridView和其他基础设置

        // 从数据库中加载收入相关的类型列表
        List<TypeBean> inlist = DBManager.getTypeList(1); // 假设1代表收入类型

        // 将加载到的收入类型列表添加到typeList中
        typeList.addAll(inlist);

        // 通知GridView适配器数据已经改变，需要重新加载数据
        adapter.notifyDataSetChanged();

        // 设置默认的类型名称和图标
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qt_fs); // 这是收入类型的默认图标资源
    }

    @Override
    public void saveAccountToDB() {
        // 设置账户类型为收入（假设1代表收入）
        accountBean.setKind(1);

        // 将账户数据插入到数据库中
        DBManager.insertItemToAccounttb(accountBean);
    }
}
