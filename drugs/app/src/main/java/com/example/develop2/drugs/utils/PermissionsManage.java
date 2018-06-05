package com.example.develop2.drugs.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiejiahua on 2018/6/5.
 */
public class PermissionsManage {
    //    // Storage Permissions
//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//    /**
//     * Checks if the app has permission to write to device storage
//     * <p>
//     * If the app does not has permission then the user will be prompted to
//     * grant permissions
//     *
//     * @param activity
//     */
//    public  static void  verifyStoragePermissions(Activity activity) {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(activity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE);
//        }
//    }
    //相机权限检测请求码
    public final static int PERMISSIONCODE = 999;
    //相机权限
    public final static int CAME = 0;
    //sd卡读写权限
    public final static int SD = 1;
    public final static int SD1 = 2;
    //短信
    public final static int INFOMASTION = 3;
    //电话
    public final static int PHONE = 4;
    //精确定位
    public final static int GPS = 5;

    //权限集合：
    public static String[] pers = {Manifest.permission.CAMERA, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS,
            Manifest.permission.CALL_PRIVILEGED, Manifest.permission.ACCESS_FINE_LOCATION};

    //当前需要申请的权限集合
    public static int[] permissions;

    //其他权限类似可自行添加

    /**
     * 根据需求输入对应的需要检测申请的权限
     *
     * @param permission
     */
    public static void setPermission(int... permission) {
        permissions = permission;
    }

    /**
     * 检测手机权限，如果没有提出申请
     * mtype
     */
    public static void checkPermission(Activity activity, int... permission) {
        setPermission(permission);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i] >= pers.length) {
                //执行此步骤标识，代码检测的权限值集合包含越界值
                Toast.makeText(activity, "权限数值不合法", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((ContextCompat.checkSelfPermission(activity, pers[permissions[i]])) != PackageManager.PERMISSION_GRANTED) {
                list.add(pers[permissions[i]]);
            }
        }
        if (list.size() > 0) {
            String[] a = new String[list.size()];
            list.toArray(a);
            //申请权限
            ActivityCompat.requestPermissions(activity, a, PERMISSIONCODE);
        }
    }
}