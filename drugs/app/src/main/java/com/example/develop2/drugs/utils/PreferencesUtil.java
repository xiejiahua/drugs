package com.example.develop2.drugs.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class PreferencesUtil {

    public static SharedPreferences getDefaultPreferences(Context context, String prefName) {
        SharedPreferences sp = null;
        if (context != null) {
            sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
        return sp;
    }
}
