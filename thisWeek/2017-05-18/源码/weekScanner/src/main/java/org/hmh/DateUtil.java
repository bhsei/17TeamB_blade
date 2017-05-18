package org.hmh;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created this one by HMH on 2017/5/15.
 */
public class DateUtil {
    public static String getModifiedTime(File file, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(file.lastModified());
    }

    public static String getModifiedTime(File file) {
        return getModifiedTime(file,"yyyy-MM-dd");
    }

    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static Date getFirstDayOfWeek() {
        Calendar calendar = new GregorianCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < 7) {
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear - 1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, 6);
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek() {
        Calendar calendar = new GregorianCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek >= 7) {
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear + 1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        return calendar.getTime();
    }
}
