package com.example.workapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    // 静态变量，用于存储SQLiteDatabase实例，实现单例模式
    private static SQLiteDatabase db;

    // 初始化数据库，传入上下文对象，并获取可写的数据库实例
    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    // 根据类型种类（kind）从typetb表中获取类型列表
    public static List<TypeBean> getTypeList(int kind) {
        List<TypeBean> list = new ArrayList<>();
        String sql = "select * from typetb where kind = " + kind;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);
        }
        return list;
    }

    // 向accounttb表中插入一条账户记录
    public static void insertItemToAccounttb(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put("typename", bean.getTypename());
        values.put("sImageId", bean.getsImageId());
        values.put("beizhu", bean.getBeizhu());
        values.put("money", bean.getMoney());
        values.put("time", bean.getTime());
        values.put("year", bean.getYear());
        values.put("month", bean.getMonth());
        values.put("day", bean.getDay());
        values.put("kind", bean.getKind());
        db.insert("accounttb", null, values);
        Log.i("animee", "insertItemToAccounttb:ok");
    }

    // 根据年、月、日从accounttb表中获取一天的账户记录列表
    public static List<AccountBean> getAccountListOneDayFromAccounttb(int year, int month, int day) {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});

        while (cursor.moveToNext()) {
           @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range")String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range")String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            @SuppressLint("Range")String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range")int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range")int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range")float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    // 根据年、月从accounttb表中获取一个月的账户记录列表
    public static List<AccountBean> getAccountListOneMonthFromAccounttb(int year, int month) {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=?  order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range")String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range")String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            @SuppressLint("Range")String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range")int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range")int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range")float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }




    public static List<Integer>getYearListFromAccounttb(){
        List<Integer>list = new ArrayList<>();
        String sql = "select distinct(year) from accounttb order by year asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range")
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        return list;
    }

    // 根据年、月、日和类型种类从accounttb表中获取一天内某类交易的总金额
    public static float getSumMoneyOneDay(int year, int month, int day, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range")
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    // 根据年、月和类型种类从accounttb表中获取一个月内某类交易的总金额
    public static float getSumMoneyOneMonth(int year, int month, int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range")
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    public static int deleteItemFromAccounttbById(int id) {
        int i = db.delete("accounttb", "id=?", new String[]{id + ""});
        return i;
    }
}






