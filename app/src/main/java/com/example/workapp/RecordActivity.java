package com.example.workapp;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.workapp.adapter.RecordPagerAdapter;
import com.example.workapp.frag_record.IncomeFragment;
import com.example.workapp.frag_record.OutcomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    // TabLayout控件，用于展示标签页
    TabLayout tabLayout;
    // ViewPager控件，用于展示不同的片段
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record); // 设置活动的布局文件

        // 初始化TabLayout和ViewPager控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);

        // 初始化ViewPager和TabLayout的数据和配置
        initPager();
    }
    // 初始化ViewPager和TabLayout的方法
    private void initPager() {
        // 创建一个片段列表，用于存储要展示的片段
        List<Fragment> fragmentList = new ArrayList<>();

        // 创建支出片段和收入片段的实例
        OutcomeFragment outFrag = new OutcomeFragment();
        IncomeFragment inFrag = new IncomeFragment();

        // 将片段添加到列表中
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        // 创建一个RecordPagerAdapter实例，并将片段列表和SupportFragmentManager传递给它
        RecordPagerAdapter pagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);

        // 将适配器设置给ViewPager
        viewPager.setAdapter(pagerAdapter);

        // 将TabLayout与ViewPager关联起来，这样TabLayout就可以自动根据ViewPager的内容更新标签页了
        tabLayout.setupWithViewPager(viewPager);
    }

    // onClick方法用于处理视图的点击事件

    public void onClick(View view) {
        // 如果点击的视图ID是record_iv_back（可能是返回按钮），则启动HomeActivity
        if (view.getId() == R.id.record_iv_back) {
            Intent intent = new Intent(this, HomeActivity.class);
            // 启动主页的Activity
            startActivity(intent);
        }
    }
}