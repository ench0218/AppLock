package com.ench_wu.applock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * author:ench_wu
 * Created on 2015/9/20.
 */
public class AppLockOpenHelper extends android.database.sqlite.SQLiteOpenHelper{


    public AppLockOpenHelper(Context context) {
        super(context, "info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement,packageName char(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
