package utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by HMH on 2017/4/13.
 * JSON操作转换库
 */
public class JsonUtil {

    /**
     * 转换pojo对象为json格式
     * @param obj 对象
     * @return json格式字符串
     */
    public static <T> String toJson(T obj) {
        String json = null;
        try {
            json = JSON.toJSONString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 从Json转换为pojo
     * @param json json字符串
     * @param type 转换到的类型
     * @return 从json生成的对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo = null;
        try {
            pojo = JSON.parseObject(json,type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
