package com.example.workapp.adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

// 定义一个继承自FragmentPagerAdapter的适配器类，用于ViewPager中的Fragment页面展示
public class RecordPagerAdapter extends FragmentPagerAdapter {

    // 成员变量：存储Fragment列表和页面标题数组
    List<Fragment> fragmentList;
    String[] titles = {"支出", "收入"}; // 页面标题，对应支出和收入两个Fragment

    // 构造函数，传入FragmentManager和Fragment列表
    public RecordPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm); // 调用父类构造函数
        this.fragmentList = fragmentList; // 初始化Fragment列表
    }

    // 重写getItem方法，根据位置返回对应的Fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position); // 返回列表中指定位置的Fragment
    }

    // 重写getCount方法，返回Fragment的数量
    @Override
    public int getCount() {
        return fragmentList.size(); // 返回Fragment列表的大小
    }

    // 重写getPageTitle方法，返回指定位置的页面标题
    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position]; // 返回标题数组中指定位置的标题
    }
}