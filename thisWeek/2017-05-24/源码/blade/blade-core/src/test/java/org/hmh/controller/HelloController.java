package org.hmh.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

/**
 * Created this one by HMH on 2017/5/2.
 */
@Controller
public class HelloController {
    @Inject
    public org.hmh.service.HelloService helloService;

    @Route(values = "/")
    public String index() {
        return "index.html";
    }

    @Route(values = "/hello")
    public String sayParam(Request request, Response response) {
        String name = request.query("name");
        return name+".html";
    }

    public String sayHello() {
        System.out.println(" --- Say before in Controller");
        helloService.sayHello();
        System.out.println(" --- Say after in Controller");
        return "hello";
    }
}
