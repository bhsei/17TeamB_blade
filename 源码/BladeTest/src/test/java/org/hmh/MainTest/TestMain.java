package org.hmh.MainTest;

import cn.hhchat.BladeTest.annotation.InterceptorClasses;
import cn.hhchat.BladeTest.interceptor.TimeInterceptor;
import cn.hhchat.BladeTest.mock.MockRequest;
import cn.hhchat.BladeTest.mock.MockResponse;
import cn.hhchat.BladeTest.mock.RouteMock;
import cn.hhchat.BladeTest.annotation.BladeConfiguration;
import cn.hhchat.BladeTest.runner.BladeTest4JUnitRunner;
import com.blade.ioc.annotation.Inject;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.multipart.MultipartException;
import org.hmh.service.HelloService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created this one by HMH on 2017/5/10.
 */
@RunWith(BladeTest4JUnitRunner.class)
@BladeConfiguration("org.hmh")
@InterceptorClasses({TimeInterceptor.class})
public class TestMain {

    @Inject
    private RouteMock routeMock;

    @Inject
    private HelloService helloService;

    @Inject
    private org.hmh.controller.HelloController HelloController;

    @Test
    public void test1() {
        helloService.sayHello();
        HelloController.sayHello();
    }

    @Test
    public void Test2() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest request = new MockRequest();
        request.parameter("name", "hello");

        Assert.assertEquals(routeMock.Request("/hello", HttpMethod.GET,request,new MockResponse()),"hello.html");

    }

}
