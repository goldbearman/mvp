package com.hfad.aplicationmanageraplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class AppManager {

    private final PackageManager packageManager;

    public AppManager(Context context) {
        packageManager = context.getPackageManager();
    }
    //Получаем список приложений
    public List<AppInfo> getInstalledApp() {
        //Получаем список установленных пакетов
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        List<AppInfo> instaledApps = new ArrayList<>();
        // Преобразуем в список AppInfo
        for (PackageInfo installedPackage : installedPackages) {
           AppInfo appInfo= new AppInfo(installedPackage.packageName,
                    installedPackage.versionCode,
                    installedPackage.versionName,
                    installedPackage.applicationInfo.loadLabel(packageManager).toString(),
                    installedPackage.applicationInfo.loadIcon(packageManager));
            instaledApps.add(appInfo);
        }
        return instaledApps;
    }
}







