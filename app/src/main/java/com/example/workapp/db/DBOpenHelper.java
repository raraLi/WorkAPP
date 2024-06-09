package com.example.workapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.workapp.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    // 构造函数，用于创建DBOpenHelper对象
    public DBOpenHelper(@Nullable Context context) {
        super(context, "workapp.db",null,1);
    }
    // 当数据库首次创建时调用此方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,sImagId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);

        // 创建accounttb表，用于存储账户数据
        sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,beizhu varchar(80),money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);

    }

    private void insertType(SQLiteDatabase db) {

        String sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sql,new Object[]{"其他", 1,1,0});
        db.execSQL(sql,new Object[]{"餐饮", 2,2,0});
        db.execSQL(sql,new Object[]{"交通", 3,3,0});
        db.execSQL(sql,new Object[]{"购物", 4,4,0});
        db.execSQL(sql,new Object[]{"服饰", 5,5,0});
        db.execSQL(sql,new Object[]{"日用品", 6,6,0});
        db.execSQL(sql,new Object[]{"娱乐", 7,7,0});
        db.execSQL(sql,new Object[]{"零食", 8,8,0});
        db.execSQL(sql,new Object[]{"烟酒茶", 9,9,0});
        db.execSQL(sql,new Object[]{"学习", 10,10,0});
        db.execSQL(sql,new Object[]{"医疗", 11,11,0});
        db.execSQL(sql,new Object[]{"住宅", 12,12,0});
        db.execSQL(sql,new Object[]{"水电",13,13,0});
        db.execSQL(sql,new Object[]{"通讯", 14,14,0});
        db.execSQL(sql,new Object[]{"人情往来",15,15,0});

        db.execSQL(sql,new Object[]{"其他", 16,16,1});
        db.execSQL(sql,new Object[]{"薪资", 17,17,1});
        db.execSQL(sql,new Object[]{"奖金", 18,18,1});
        db.execSQL(sql,new Object[]{"借入", 19,19,1});
        db.execSQL(sql,new Object[]{"收债", 20,20,1});
        db.execSQL(sql,new Object[]{"利息收入",21,21,1});
        db.execSQL(sql,new Object[]{"投资回报", 22,22,1});
        db.execSQL(sql,new Object[]{"二手交易", 23,23,1});
        db.execSQL(sql,new Object[]{"意外所得", 24,24,1});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
