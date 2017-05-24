package com.blade.ioc;

import com.blade.ioc.annotation.Service;
import com.blade.ioc.loader.IocAnnotationLoader;
import com.blade.ioc.loader.IocLoader;
import javafx.collections.transformation.FilteredList;
import org.hmh.controller.HelloController;
import org.hmh.model.User;
import org.hmh.service.HelloService;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created this one by HMH on 2017/5/23.
 */
public class FieldInjectorTest {
    @Test
    public void injection() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        //添加Bean
        HelloController controller = new HelloController();
        //获取字段
        HelloService helloService = new HelloService();
//        ioc.addBean(helloService);
        assert controller.helloService == null;
        //填充Bean
        List<Field> fields = Arrays.stream(HelloController.class.getFields()).filter(f -> !f.isAnnotationPresent(Service.class)).collect(Collectors.toList());
        fields.forEach(f->new FieldInjector(ioc,f).injection(controller));
        assert controller.helloService != null;

    }

}