package com.example.workapp.frag_record;
import androidx.fragment.app.Fragment;
import com.example.workapp.R;
import com.example.workapp.db.DBManager;
import com.example.workapp.db.TypeBean;

import java.util.List;

public class OutcomeFragment extends  BaseFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertItemToAccounttb(accountBean);
    }

}