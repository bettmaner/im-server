package edu.ncu.zww.imserver.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    public static String getDateCN() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2019年01月03日 23:41:31
    }

    public static String getDateEN() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(new Date(System.currentTimeMillis()));
        return date1;// 2019-01-03 23:41:31
    }
}
