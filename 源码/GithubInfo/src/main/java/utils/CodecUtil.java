package utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Created by HMH on 2017/4/13.
 */
public final class CodecUtil {

    /**
     * 将URL编码
     * @param source 源url
     * @return
     */
    public static String encodeURL(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return target;
    }

    /**
     * 按照UTF-8解码
     * @param source
     * @return
     */
    public static String decodeURL(String source) {
        String target = null;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return target;
    }

}
