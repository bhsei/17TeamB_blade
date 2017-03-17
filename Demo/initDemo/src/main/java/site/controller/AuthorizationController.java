package site.controller;

import site.utils.AuthorizationUtil;
import site.bean.userForm;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

/**
 * Created by HMH on 2017/3/17.
 */
@Controller
public class AuthorizationController {

    @Route(values = "/", method = HttpMethod.GET)
    public String home() {
        return "index";
    }

    @Route(values = {"/postForm"})
    public void trans(Request request, Response response) {
        String username=request.query("username");
        String password = request.query("password");
        userForm user = new userForm(username,password);
        response.text(AuthorizationUtil.trans(user));
    }
}
