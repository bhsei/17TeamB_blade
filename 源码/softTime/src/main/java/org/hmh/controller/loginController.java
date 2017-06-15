package org.hmh.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.wrapper.Session;
import org.hmh.model.Event;
import org.hmh.model.user;
import org.hmh.service.*;
import java.io.IOException;
import java.util.Date;

/**
 * Created this one by HMH on 2017/4/18.
 */
@Controller
public class loginController {

    @Inject
    private userService userService;

    @Inject
    private EventService eventService;

    /**
     * 登陆页面
     */
    @Route("/login")
    public String getLogin(Session session, Request request) {
        return "login.html";
    }

    /**
     * 工作页面
     */
    @Route(value = "/working")
    public String getWorking(Session session, Request request,Response response) throws Exception, IOException {
        //获取表单信息
        String username = request.query("username");
        String password = request.query("password");
        if (username == null || password == null) {
            response.go("/login");
            return null;
        }
        user user = userService.getUser(username, password);
        if (user == null) {
            response.go("/login");
            return null;
        }
        session.attribute("user", user);
        //获取未完成内容
        return "working";
    }

    /**
     * 提交
     */
    @Route("/count")
    public void Count(Session session, Request request, Response response) throws Exception{
        String username = request.query("username");
        String password = request.query("password");
        if (username == null || password == null) {
            response.go("/login");
            return;
        }
        user user = userService.getUser(username, password);
        Event event = eventService.getActiveEvent(user);
        if (request.method().equals("GET")) {
            if (event == null) {
                event = new Event();
                event.setStart_time(new Date().getTime());
                event.setUser_id(user.getId());
                event.setState(1);
                event.setRepo_id(user.getRepo_id());
                eventService.save(event);
                event = eventService.getActiveEvent(user);
                response.writer().write("任务 #"+event.getId()+" 开始");
                return;
            }
            response.writer().write("继续任务#"+event.getId());
            return;
        }else{
            if (event == null) {
                response.writer().write("没有活跃的任务正在进行");
                return;
            }
            String wrong = request.query("wrong");
            String plan = request.query("plan");
            String thing = request.query("do");
            event.setEnd_time(new Date().getTime());
            event.setMessage(thing);
            event.setSome_thing_wrong(wrong);
            event.setPlan(plan);
            event.setState(2);
            event.setPercent((event.getEnd_time()-event.getStart_time())*100/3600/1000/event.getWork_time_of_day());
            eventService.update(event);
            response.writer().write("任务#"+event.getId()+"提交完成");
        }
    }

    /**
     * 取消
     */
    @Route("/cancel")
    public void cancel(Session session, Request request, Response response) throws Exception {
        String username = request.query("username");
        String password = request.query("password");
        if (username == null || password == null) {
            response.go("/login");
            return;
        }
        user user = userService.getUser(username, password);
        Event event = eventService.getActiveEvent(user);
        if (event == null) {
            response.writer().write("没有正在进行的任务");
            return;
        }
        event.setState(3);
        eventService.update(event);
        response.writer().write("任务"+event.getId()+"已经取消");
    }
}
