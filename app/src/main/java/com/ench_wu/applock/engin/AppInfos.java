package com.ench_wu.applock.engin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ench_Wu on 2015/9/12.
 */
public class AppInfos {


    public static List<AppInfo> getAppInfos(Context context) {

        List<AppInfo> packageAppInfos = new ArrayList<AppInfo>();
        //获取到包的管理者
        PackageManager packageManager = context.getPackageManager();
        //获取到安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        for (PackageInfo installedPackage : installedPackages) {
            AppInfo appInfo = new AppInfo();
            //获得Icon
            Drawable icon = installedPackage.applicationInfo.loadIcon(packageManager);

            appInfo.setIcon(icon);
            //获得apk名
            String apkName = installedPackage.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setApkName(apkName);
            //获得包名
            String packageName = installedPackage.packageName;
            appInfo.setApkPackageName(packageName);
            //获得包路径
            String sourceDir = installedPackage.applicationInfo.sourceDir;

            File file = new File(sourceDir);

            long apkSize = file.length();
            appInfo.setApkSize(apkSize);



            int flags = installedPackage.applicationInfo.flags;
            if ((flags& ApplicationInfo.FLAG_SYSTEM)!=0){
                //表示系统app
                appInfo.setUserApp(false);
            }else {
                appInfo.setUserApp(true);
            }

            if ((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0){
                appInfo.setIsRom(false);
            }else {
                appInfo.setIsRom(true);
            }


            packageAppInfos.add(appInfo);
        }
        return packageAppInfos;
    }

}
