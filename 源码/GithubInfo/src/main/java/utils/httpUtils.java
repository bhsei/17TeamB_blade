package utils;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Created by HMH on 2017/3/23.
 */
public class httpUtils {


    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client != null) {
            return client;
        }
        client = new OkHttpClient();
        return client;
    }


    public static void post(String url) {

    }

    public static Response get(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = doGet(request);
        return response;
    }

    public static Response get(String url, Headers headers) {
        Request request = new Request.Builder().url(url).headers(headers).build();
        Response response = doGet(request);
        return response;
    }

    public static Response get(Request request) {
        Response response = doGet(request);
        return response;
    }


    public static Response doGet(Request request){
        Response response = null;
        try {
            response = getClient().newCall(request).execute();
            if (response.isSuccessful()) {
                return response;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

}
