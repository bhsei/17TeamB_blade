package utils;

import java.text.DecimalFormat;

/**
 * Created by HMH on 2017/4/18.
 */
public class ProgressUtil {

    private static long minimum = 0; // 进度条起始值

    private static long maximum = 100; // 进度条最大值

    private static long barLen = 100; // 进度条长度

    private static char showChar = '#'; // 用于进度条显示的字符

    private static DecimalFormat formater = new DecimalFormat("#.##%");

    /**
     * 使用系统标准输出，显示字符进度条及其百分比。
     */
    public ProgressUtil() {
    }

    /**
     * 使用系统标准输出，显示字符进度条及其百分比。
     *
     * @param minimum 进度条起始值
     * @param maximum 进度条最大值
     * @param barLen 进度条长度
     */
    public ProgressUtil(long minimum, long maximum,
                        long barLen) {
        this(minimum, maximum, barLen, '=');
    }

    /**
     * 使用系统标准输出，显示字符进度条及其百分比。
     *
     * @param minimum 进度条起始值
     * @param maximum 进度条最大值
     * @param barLen 进度条长度
     * @param showChar 用于进度条显示的字符
     */
    public ProgressUtil(long minimum, long maximum,
                        long barLen, char showChar) {
        ProgressUtil.minimum = minimum;
        ProgressUtil.maximum = maximum;
        ProgressUtil.barLen = barLen;
        ProgressUtil.showChar = showChar;
    }

    /**
     * 显示进度条。
     *
     * @param value 当前进度。进度必须大于或等于起始点且小于等于结束点（start <= current <= end）。
     */
    public static void show(long value) {
        minimum = value;
        float rate = (float) (minimum*1.0 / maximum);
        long len = (long) (rate * barLen);
        draw(len, rate);
        afterComplete();
    }

    private static void draw(long len, float rate) {
        for (int i = 0; i < len; i++) {
            System.out.print(showChar);
        }
        System.out.print(' ');
        System.out.print(format(rate));
    }


    private static void afterComplete() {
        System.out.print('\n');
    }

    private static String format(float num) {
        return formater.format(num);
    }



}
