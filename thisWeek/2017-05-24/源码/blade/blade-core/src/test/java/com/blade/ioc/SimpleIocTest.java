package com.blade.ioc;

import com.blade.ioc.loader.IocAnnotationLoader;
import com.blade.ioc.loader.IocLoader;
import org.hmh.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Set;

/**
 * Created this one by HMH on 2017/5/23.
 */
public class SimpleIocTest {

    @Before
    public void init() {

    }

    @Test
    public void load1() throws Exception {
        IocLoader loader1 = new IocAnnotationLoader("org.hmh.model");
        SimpleIoc ioc1 = new SimpleIoc();
        ioc1.load(loader1);
        //期望ioc里面包括所有的标注了component的对象
        List<BeanDefine> beanDefines = ioc1.getBeanDefines();
        beanDefines.forEach(b->System.out.println(b.getType()));
    }

    @Test
    public void load2() throws Exception {
        IocLoader loader2 = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc2 = new SimpleIoc();
        ioc2.load(loader2);
        //期望ioc里面包括所有的标注了component的对象
        List<BeanDefine> beanDefines = ioc2.getBeanDefines();
        beanDefines.forEach(b->System.out.println(b.getType()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void load3() throws Exception {
        IocLoader loader3 = new IocAnnotationLoader("");
        SimpleIoc ioc3 = new SimpleIoc();
        ioc3.load(loader3);
        //期望ioc里面包括所有的标注了component的对象
        List<BeanDefine> beanDefines = ioc3.getBeanDefines();
        beanDefines.forEach(b->System.out.println(b.getType()));
    }

    @Test
    public void addBean() throws Exception {
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        ioc.addBean(User.class);
        List<BeanDefine> beanDefines = ioc.getBeanDefines();
        beanDefines.forEach(b->System.out.println(b.getType()));
    }

    @Test
    public void setBean() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        ioc.addBean(User.class);
        //添加Bean
        String usernameO = ioc.getBean(User.class).getUsername();
        //设置新的Bean
        User user = new User("testSetBean", "testSetBean");
        ioc.setBean(User.class, user);
        //取出结果
        String username1 = ioc.getBean(User.class).getUsername();
        //期望设置前后不一样
        assert !username1.equals(usernameO);
        //期望设置以后和设置的值相同
        assert username1.equals("testSetBean");
    }

    @Test
    public void getBeanDefine() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        //添加Bean
        User user = new User("getBeanDefine", "getBeanDefine");
        ioc.addBean(user);
        //获取BeanDefine
        BeanDefine bd = ioc.getBeanDefine(User.class, true);
        //期望返回的类型定义相同
        assert bd.getType() == User.class;
        //期望返回的对象是一个新对象
        assert bd.getBean() != user;

    }

    @Test
    public void getBean() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        //添加Bean
        User user = new User("getBean", "getBean");
        ioc.addBean(user);
        //获取BeanDefine
        User userBean = ioc.getBean(User.class);
        //期望返回的是同一个对象
        assert user == userBean;

    }

    @Test
    public void getBeans() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        List<Object> beans = ioc.getBeans();
        //期望返回类型与预期相同
        beans.forEach(b->System.out.println(b.getClass()));
    }

    @Test
    public void getBeanNames() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        Set<String> beans = ioc.getBeanNames();
        //期望返回类型与预期相同
        beans.forEach(System.out::println);
    }

    @Test
    public void clearAll() throws Exception {
        //初始化
        IocLoader loader = new IocAnnotationLoader("org.hmh");
        SimpleIoc ioc = new SimpleIoc();
        ioc.load(loader);
        ioc.clearAll();
        assert ioc.getBeans().size() == 0;
    }

}