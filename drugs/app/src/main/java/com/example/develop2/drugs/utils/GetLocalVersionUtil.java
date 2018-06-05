package com.example.develop2.drugs.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class GetLocalVersionUtil {

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    "com.campusstreet", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.campusstreet", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg", e.getMessage());
        }
        return verName;
    }
}
