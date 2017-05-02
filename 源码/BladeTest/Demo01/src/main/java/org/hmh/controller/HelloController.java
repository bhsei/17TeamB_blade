package org.hmh.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.Route;
import org.hmh.service.*;

/**
 * Created this one by HMH on 2017/5/2.
 */
@Controller
public class HelloController {
    @Inject
    private HelloService HelloService;

    @Route(values = "/")
    public String index() {
        return "index.html";
    }

    public void sayHello() {
        System.out.println("Say before Controller");
        HelloService.sayHello();
        System.out.println("Say after Controller");


    }
}
