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
        super.loadDataToGv();
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);
    }
}