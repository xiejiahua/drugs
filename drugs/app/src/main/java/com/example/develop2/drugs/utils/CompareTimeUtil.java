package com.example.develop2.drugs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class CompareTimeUtil {

    public static boolean compareTime(String time) {
        time = time.replaceAll("/", "-");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = CurrentTime.format(curDate);
        try {
            c1.setTime(CurrentTime.parse(time));
            c2.setTime(CurrentTime.parse(dataStrNew));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            return false;
        } else if (result < 0) {
            return false;
        } else {
            return true;
        }
    }
}
