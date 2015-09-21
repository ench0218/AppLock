package com.ench_wu.mylibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * 
 * �� Ȩ �� �������Ա�������� ��Ȩ���� (c) 2015
 * 
 * �� �� : ��ΰ��
 * 
 * �� �� �� 1.0
 * 
 * �������� �� 2015-3-7 ����10:14:24
 * 
 * �� �� ��
 * 
 * �����������ݿ� �޶���ʷ ��
 * 
 * ============================================================
 **/
public class AppLockDao {

	private AppLockOpenHelper helper;

	public AppLockDao(Context context) {
		helper = new AppLockOpenHelper(context);
	}

	/**
	 * ��ӵ�����������
	 * 
	 * @param packageName
	 *            ����
	 */
	public void add(String packageName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("packagename", packageName);
		db.insert("info", null, values);
		db.close();
	}

	/**
	 * �ӳ���������ɾ����ǰ�İ�
	 * 
	 * @param packageName
	 */
	public void delete(String packageName) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("info", "packagename=?", new String[] { packageName });
		db.close();
	}
    /**
     * ��ѯ��ǰ�İ��Ƿ��ڳ���������
     * @param packageName
     * @return
     */
	public boolean find(String packageName) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("info", null, "packagename=?",
				new String[] { packageName }, null, null, null);
		if (cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		db.close();
		return result;

	}
	/**
	 * ��ѯȫ���������İ���
	 * @return
	 */
	public List<String> findAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("info", new String[]{"packagename"}, null, null, null, null, null);
		List<String> packnames = new ArrayList<String>();
		while(cursor.moveToNext()){
			packnames.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return packnames;
	}
	
}
