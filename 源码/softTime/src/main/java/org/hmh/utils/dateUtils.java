package org.hmh.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created this one by HMH on 2017/4/27.
 */
public class dateUtils {

    public static Date getWeekStartDate(){
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        if(today>6)
            cal.set(Calendar.WEEK_OF_YEAR,week);
        else
            cal.set(Calendar.WEEK_OF_YEAR,week-1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
    public static Date getWeekEndDate(){
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        if(today>6)
            cal.set(Calendar.WEEK_OF_YEAR,week+1);
        else
            cal.set(Calendar.WEEK_OF_YEAR,week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
}
