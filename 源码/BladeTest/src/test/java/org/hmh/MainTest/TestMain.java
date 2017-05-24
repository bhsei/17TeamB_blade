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
import org.hmh.model.User;
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

    //测试IOC
    @Test
    public void test1() {
        helloService.sayHello();
        HelloController.sayHello();
    }

    //测试页面
    @Test
    public void Test2() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest request = new MockRequest();
        request.parameter("name", "hello");

        Assert.assertEquals(routeMock.Request("/hello", HttpMethod.GET,request,new MockResponse()),"hello.html");
    }

    //测试返回数据
    @Test
    public void Test3() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest getRequest = new MockRequest();
        getRequest.parameter("username", "hello");
        User user = new User("hello","hello");

        Assert.assertEquals(routeMock.Request("/user", HttpMethod.GET,getRequest,new MockResponse()),user);

//        getRequest.parameter("username", "hello!");
//        Assert.assertEquals(routeMock.Request("/user", HttpMethod.GET, request, new MockResponse()), user);

    }

    //test测试添加用户
    @Test
    public void Test4() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest addRequest = new MockRequest();

        addRequest
                .parameter("username", "user1")
                .parameter("password","pwd1");

        Assert.assertEquals(routeMock.Request("/addUser", HttpMethod.GET,addRequest,new MockResponse()),true);

    }

    //模拟登陆测试
    @Test
    public void Test5() throws InvocationTargetException, IllegalAccessException, MultipartException, IOException {

        MockRequest addRequest = new MockRequest();

        addRequest
                .parameter("username", "user1")
                .parameter("password","pwd1");

        Assert.assertEquals(routeMock.Request("/addUser", HttpMethod.GET,addRequest,new MockResponse()),true);


        MockRequest loginRequest = new MockRequest();

        loginRequest.parameter("username", "user1").parameter("password","pwd1");

        Assert.assertEquals(routeMock.Request("/login", HttpMethod.GET,loginRequest,new MockResponse()),true);

    }

}
