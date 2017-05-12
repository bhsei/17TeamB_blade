package org.hmh.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
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
    public String getUser(Request request, Response response) {
        String id = request.query("id");
        String name = userService.getName(new Integer(id));
        return name;
    }
    @Route(values = "/addUser" )
    public boolean setUser(Request request,Response response){
        String id =  request.query("id");
        String name = request.query("name");
       // System.out.println(id+name);
        userService.setUser(new Integer(id),name);
        boolean success = userService.storeToDB();
        return success;
    }
    public void sayHello(){
        PrintStream out = System.out;
        out.println(" --- before Controller");

        userService.setId(1);
        String name = userService.getName();
        out.println("id:"+1+" 对应的User:"+name);

        userService.setUser(12,"User#12");
        userService.storeToDB();
        userService.setUser(12,null);
        name = userService.getName();
        out.println("id:"+12+" 对应的User:"+name+"已经添加进数据库");

        userService.setId(12);
        userService.removeFromDB();
        name = userService.getName();
        assert (name == null);
        out.println("id:"+12+" 对应的User已经被从数据库中删除");


        out.println(" --- after Controller");
    }
}
