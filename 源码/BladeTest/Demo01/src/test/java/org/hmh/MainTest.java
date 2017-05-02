package org.hmh;

import com.blade.Blade;
import com.blade.ioc.*;
import com.blade.ioc.annotation.Inject;
import org.hmh.service.HelloService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created this one by HMH on 2017/5/2.
 */

public class MainTest {

    private Ioc ioc;
    Blade blade=Blade.$();

    @Before
    public void init() throws Exception {
        blade.bConfig().addScanPackage("org.hmh");
        Set<String> pkgs = blade.bConfig().getPackages();
        IocApplication iocApplication = new IocApplication();
        iocApplication.initBeans();
        ioc = blade.ioc();

        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(int i=0;i<fields.length;i++) {
            if (fields[i].isAnnotationPresent(Inject.class)) {
                Class<?> fieldClass = fields[i].getType();
                Object value = ioc.getBean(fieldClass);
                if (value == null) {
                    throw new Exception("ioc初始化失败，字段"+fieldClass.getSimpleName()+"没有可依赖的对象");
                }
                fields[i].setAccessible(true);
                fields[i].set(this, value);
            }
        }
    }

    @Inject
    HelloService injectService;
    @Test
    public void testIoc() throws Exception {
        HelloService HelloService = ioc.getBean(HelloService.class);
        HelloService.sayHello();
    }

    @Test
    public void auto() {
        injectService.sayHello();

    }

}
