package com.blade.ioc.loader;

import com.blade.ioc.BeanDefine;
import com.blade.ioc.SimpleIoc;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created this one by HMH on 2017/5/23.
 */
public class IocAnnotationLoaderTest {
    @Test
    public void load() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        //期望ioc里面包括所有的标注了component的对象
        List<BeanDefine> beanDefines = ioc.getBeanDefines();
        beanDefines.forEach(b->System.out.println(b.getType()));
    }

}