package controller;

import Utils.AuthorizationUtil;
import bean.userForm;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by HMH on 2017/3/12.
 */
public class AuthorizationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

    public void init(Request request, Response response){
        response.render("index.jsp");
    }

    public void trans(Request request, Response response) {
        String username=request.query("username");
        String password = request.query("password");
        userForm user = new userForm(username,password);
        response.text(AuthorizationUtil.trans(user));
    }
}
