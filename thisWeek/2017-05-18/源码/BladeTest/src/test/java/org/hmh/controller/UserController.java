package org.hmh.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import org.hmh.model.User;
import org.hmh.service.UserService;

import java.io.PrintStream;

/**
 * Created this one by liuye on 17-5-5.
 */
@Controller
public class UserController {
    @Inject
    private UserService userService;

    @Route(values = "/user" )
    public User getUser(Request request, Response response) {
        String username = request.query("username");
        User user = userService.getUser(username);
        return user;
    }
    @Route(values = "/addUser" )
    public boolean setUser(Request request,Response response) throws Exception {
        String username =  request.query("username");
        String password = request.query("password");
        return userService.storeToDB(username, password);
    }

    @Route(value = "/login")
    public boolean login(Request request,Response response) throws Exception{
        String username =  request.query("username");
        String password = request.query("password");
        User user = userService.getUser(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
