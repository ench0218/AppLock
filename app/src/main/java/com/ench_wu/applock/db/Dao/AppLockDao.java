package com.ench_wu.applock.db.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ench_wu.applock.db.AppLockOpenHelper;

/**
 * author:ench_wu
 * Created on 2015/9/20.
 */
public class AppLockDao {

    private final SQLiteOpenHelper helper;

    public AppLockDao(Context context) {
        helper = new AppLockOpenHelper(context);
    }


    public void add(String packageName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("packageName", packageName);
        db.insert("info", "packageName=?", contentValues);
        db.close();
    }

    public void delete(String packageName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("info", "packageName=?", new String[]{packageName});
        db.close();
    }
//    public void delete(String packageName) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        db.delete("info", "packagename=?", new String[] { packageName });
//        db.close();
//    }
    public boolean query(String packageName) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info", null, "packageName=?", new String[]{packageName}, null, null, null);
        boolean result = false;
        if (cursor.moveToNext()) {
            result = true;
        }
        db.close();
        cursor.close();

        return result;

    }

}
