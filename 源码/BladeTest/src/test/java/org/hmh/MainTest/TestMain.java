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

    //test controller flow
    @Test
    public void test1() {
        helloService.sayHello();
        HelloController.sayHello();
    }
    //test page get
    @Test
    public void Test2() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest request = new MockRequest();
        request.parameter("name", "hello");

        Assert.assertEquals(routeMock.Request("/hello", HttpMethod.GET,request,new MockResponse()),"hello.html");

    }
    //test value get Only
    @Test
    public void Test3() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest request = new MockRequest();
        request.parameter("id", "1");
        Assert.assertEquals(routeMock.Request("/user", HttpMethod.GET,request,new MockResponse()),"User#1");
        Assert.assertEquals(routeMock.Request("/user", HttpMethod.GET,request,new MockResponse()),"user#1");
    }
    //test value set and get
    @Test
    public void Test4() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest request = new MockRequest();
        request.parameter("id", "100");
        request.parameter("name","xxx100");
        Assert.assertEquals(routeMock.Request("/addUser", HttpMethod.GET,request,new MockResponse()),true);
        MockRequest request2 = new MockRequest();
        //error test
        // request.parameter("id", "10");
       // Assert.assertEquals(routeMock.Request("/user", HttpMethod.GET,request,new MockResponse()),"xxx100");
        request.parameter("id", "100");
        Assert.assertEquals(routeMock.Request("/user", HttpMethod.GET,request,new MockResponse()),"xxx100");

    }


}
