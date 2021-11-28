package com.board.board.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static String date2str(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String todayStr(String format) {
        return date2str(new Date(), format);
    }

    public static String today2yyyyMMdd() {
        return date2str(new Date(), "yyyyMMdd");
    }

    public static Date date2str(String date, String format) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat(format);
        return transFormat.parse(date);
    }
}
