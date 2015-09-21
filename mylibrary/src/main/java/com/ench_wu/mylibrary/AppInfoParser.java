package com.ench_wu.mylibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppInfoParser {
	/**
	 * ��ȡ�ֻ���������е�Ӧ�ó���
	 * @param context ������
	 * @return
	 */
	public static List<AppInfo> getAppInfos(Context context){
		//�õ�һ��java��֤�� ����������
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packInfos = pm.getInstalledPackages(0);
		List<AppInfo> appinfos = new ArrayList<AppInfo>();
		for(PackageInfo packInfo:packInfos){
			AppInfo appinfo = new AppInfo();
			String packname = packInfo.packageName;
			appinfo.setPackname(packname);
			Drawable icon = packInfo.applicationInfo.loadIcon(pm);
			appinfo.setIcon(icon);
			String appname = packInfo.applicationInfo.loadLabel(pm).toString();
			appinfo.setName(appname);
			//Ӧ�ó���apk����·��
			String apkpath = packInfo.applicationInfo.sourceDir;
			appinfo.setApkpath(apkpath);
			File file = new File(apkpath);
			long appSize = file.length();
			appinfo.setAppSize(appSize);
			//Ӧ�ó���װ��λ�á�
			int flags = packInfo.applicationInfo.flags; //������ӳ��  ��bit-map
			if((ApplicationInfo.FLAG_EXTERNAL_STORAGE&flags)!=0){
				//�ⲿ�洢
				appinfo.setInRom(false);
			}else{
				//�ֻ��ڴ�
				appinfo.setInRom(true);
			}
			if((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
				//ϵͳӦ��
				appinfo.setUserApp(false);
			}else{
				//�û�Ӧ��
				appinfo.setUserApp(true);
			}
			appinfos.add(appinfo);
			appinfo = null;
		}
		return appinfos;
	}
}
