package org.hmh;

import cn.hhchat.BladeTest.annotation.BladeConfiguration;
import cn.hhchat.BladeTest.annotation.InterceptorClasses;
import cn.hhchat.BladeTest.interceptor.TimeInterceptor;
import cn.hhchat.BladeTest.runner.BladeTest4JUnitRunner;
import com.blade.ioc.annotation.Inject;
import org.hmh.controller.HelloController;
import org.hmh.controller.UserController;
import org.hmh.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created this one by HMH on 2017/5/3.
 */
@RunWith(BladeTest4JUnitRunner.class)
@BladeConfiguration("org.hmh")
@InterceptorClasses({TimeInterceptor.class})
public class testRunner {
    @Inject
    HelloService helloService;

    @Inject
    HelloController HelloController;
    @Test
    public void test1() {
        helloService.sayHello();
        HelloController.sayHello();
    }

    @Test
    public void test2() {
        HelloController.sayHello();
    }

    @Inject
    UserController userController;

    @Test
    public void testUser() {
        userController.sayHello();
    }



}
