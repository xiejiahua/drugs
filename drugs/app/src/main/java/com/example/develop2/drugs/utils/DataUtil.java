package com.example.develop2.drugs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class DataUtil {
    /**
     * 设置每个阶段时间
     */
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    /**
     * 格式化时间
     *
     * @param mTime
     * @return
     */
    public static String getTimeRange(String mTime) {
        mTime = mTime.replaceAll("-", "/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = sdf.format(curDate);
        Date startTime = null;
        try {
            /**将时间转化成Date*/
            curDate = sdf.parse(dataStrNew);
            startTime = sdf.parse(mTime.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**除以1000是为了转换成秒*/
        long between = 0;
        if (startTime != null) {
            between = (curDate.getTime() - startTime.getTime()) / 1000;
        }
        int elapsedTime = (int) (between);
        if (elapsedTime < seconds_of_1minute) {

            return "刚刚";
        }
        if (elapsedTime < seconds_of_30minutes) {

            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1hour) {

            return "半小时前";
        }
        if (elapsedTime < seconds_of_1day) {

            return elapsedTime / seconds_of_1hour + "小时前";
        }
        if (elapsedTime < seconds_of_15days) {

            return elapsedTime / seconds_of_1day + "天前";
        }
        if (elapsedTime < seconds_of_30days) {

            return "半个月前";
        }
        if (elapsedTime < seconds_of_6months) {

            return elapsedTime / seconds_of_30days + "月前";
        }
        if (elapsedTime < seconds_of_1year) {

            return "半年前";
        }
        if (elapsedTime >= seconds_of_1year) {

            return elapsedTime / seconds_of_1year + "年前";
        }
        return "";
    }


    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatDateTime(String time) {
        time = time.replaceAll("-", "/");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        String timeStr = time.substring(0, time.length() - 3);
        StringBuffer buff = new StringBuffer(timeStr);
        String dataSpl = timeStr.split(" ")[0];

        if (Integer.parseInt(dataSpl.split("/")[1]) < 10) {
            buff.insert(5, "0");
        }
        if (Integer.parseInt(dataSpl.split("/")[2]) < 10) {
            buff.insert(8, "0");
        }

        timeStr = buff.toString();
        if (current.after(today)) {
            return "今天 " + timeStr.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + timeStr.split(" ")[1];
        } else {
            timeStr = timeStr.replaceAll("/", "-");
            int index = timeStr.indexOf("-") + 1;
            return timeStr.substring(index, timeStr.length());
        }
    }

    public static String formatDateToChinese(String time) {
        String newDate = "";
        newDate = time.split("/")[0] + "年" + time.split("/")[1] + "月";
        String dataSpl = time.split("/")[2];
        newDate = newDate + dataSpl.split(" ")[0] + "日 ";

        String timeSpl = dataSpl.split(" ")[1];
        String lastTime = timeSpl.substring(0, timeSpl.length() - 3);

        newDate += lastTime;
        return newDate;
    }

    public static String formateDateWithoutSecond(String time) {
        time = time.replaceAll("/", "-");
        String date = time.substring(0, time.length() - 3);
        return date;
    }

    public static boolean isTimeOverCurrentTime(String time)//时间是否超过当前时间，过期
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = sdf.format(curDate);
        Date compareTime = null;
        try {
            /**将时间转化成Date*/
            curDate = sdf.parse(dataStrNew);
            compareTime = sdf.parse(time.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (compareTime.getTime() < curDate.getTime())
        {
            return true;
        }
        return false;
    }
}
