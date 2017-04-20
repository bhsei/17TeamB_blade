package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by HMH on 2017/4/13.
 */
public class StreamUtil {

    /**
     * 从流读取字符串
     * @param is
     * @return
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                is.close();
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }
}
