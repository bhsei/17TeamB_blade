package site.utils;

import site.bean.userForm;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by HMH on 2017/3/12.
 */
public class AuthorizationUtil {

    public static String trans(userForm user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String url = "http://weixin.buaa.edu.cn/uc/wap/qyauth/check.html?mpid=149";

        String result = "";
        try {

            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("username",username));
            list.add(new BasicNameValuePair("password",password));
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");

            HttpPost postReq = new HttpPost(url);
            postReq.setEntity(uefEntity);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse getResp = httpClient.execute(postReq);
            result = EntityUtils.toString(getResp.getEntity(), "utf-8");

            System.out.println(result);

            String urlinfo = "http://weixin.buaa.edu.cn/uc/wap/qyauth/info.html?mpid=149";
            HttpGet getinfo = new HttpGet(urlinfo);
            getResp = httpClient.execute(getinfo);
            result = EntityUtils.toString(getResp.getEntity(), "utf-8");
            Document document  = Jsoup.parse(result);
            Elements tocs = document.getElementsByClass("top_pic");

            if (tocs.size() == 0) {
                throw new IOException();
            }

            Element toc = tocs.first();
            String name= toc.child(0).text();
            String id = toc.child(1).text();
            String results = new String(name + " | "+id);

            return results;

        } catch (ClientProtocolException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return "error";
    }
}
