package utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by HMH on 2017/4/12.
 */
public class ArrayUtil {

    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
