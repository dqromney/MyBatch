package com.stg.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converter utilities.
 *
 * Created by dqromney on 2/13/17.
 */
public class Converters {

    public static Long strToLong(String s) {
        Long n;
        if (s.isEmpty()) {
            return new Long(0);
        }
        if (s.contains(".")) {
            Double d = new Double(s);
            n = d.longValue();
        } else {
            n = new Long(s);
        }
        return n;
    }

    // 2017-02-17, 1999-11-18
    public static Date strToDate(String dateStr) {
            DateFormat df = new SimpleDateFormat("yyy-MM-dd");
            Date dateFromStr = null;
            try {
                dateFromStr = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateFromStr;
    }
}