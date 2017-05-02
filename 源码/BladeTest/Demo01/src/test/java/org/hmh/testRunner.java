package org.hmh;

import com.blade.ioc.annotation.Inject;
import org.Junit.Runwith.BladeTest4JUnitRunner;
import org.Junit.Runwith.annotation.BladeConfiguration;
import org.hmh.controller.HelloController;
import org.hmh.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created this one by HMH on 2017/5/3.
 */
@RunWith(BladeTest4JUnitRunner.class)
@BladeConfiguration("org.hmh")
public class testRunner {
    @Inject
    HelloService helloService;

    @Inject
    HelloController HelloController;

    @Test
    public void test() {
        helloService.sayHello();
        HelloController.sayHello();
    }
}
