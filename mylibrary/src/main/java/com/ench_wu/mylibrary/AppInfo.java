package com.ench_wu.mylibrary;

import android.graphics.drawable.Drawable;

/**
 * Ӧ�ó�����Ϣ��ҵ��bean
 * @author Administrator
 *
 */
public class AppInfo {
	
	private String apkpath;
	
	
	public String getApkpath() {
		return apkpath;
	}

	public void setApkpath(String apkpath) {
		this.apkpath = apkpath;
	}

	/**
	 * Ӧ�ó����ͼ��
	 */
	private Drawable icon;
	
	/**
	 * Ӧ�ó�������
	 */
	private String name;
	
	/**
	 * Ӧ�ó���װ��λ�ã�true�ֻ��ڴ� ��false�ⲿ�洢��
	 */
	private boolean inRom;
	
	/**
	 * Ӧ�ó���Ĵ�С
	 */
	private long appSize;
	
	/**
	 * �Ƿ����û�����  true �û����� false ϵͳ����
	 */
	private boolean userApp;
	
	/**
	 * Ӧ�ó���İ���
	 */
	private String packname;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInRom() {
		return inRom;
	}

	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}

	public long getAppSize() {
		return appSize;
	}

	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public boolean isUserApp() {
		return userApp;
	}

	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	@Override
	public String toString() {
		return "AppInfo [name=" + name + ", inRom=" + inRom + ", appSize="
				+ appSize + ", userApp=" + userApp + ", packname=" + packname
				+ "]";
	}
}
