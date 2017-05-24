package com.blade.config;

import org.junit.Test;

/**
 * Created by OovEver on 2017/5/24.
 */
public class BConfigTest {
    BConfig b=new BConfig();
    @Test
    public void setEnv() throws Exception {

    }

    @Test
    public void isDev() throws Exception {
//    判断是否为开发者模式
        System.out.println(b.isDev());
    }

    @Test
    public void getEncoding() throws Exception {
//获取encoding内容
        System.out.println(b.getEncoding());
    }

    @Test
    public void isInit() throws Exception {
//判断是否初始化
        System.out.println(b.isInit());
    }

    @Test
    public void webRoot() throws Exception {

    }

    @Test
    public void getApplicationClass() throws Exception {
//获得applicationClass
        System.out.println(b.getApplicationClass());
    }

    @Test
    public void setApplicationClass() throws Exception {

    }

    @Test
    public void setBasePackage() throws Exception {
//设置基本配置包
        String basePackage1="com.blade.config1";
        String basePackage2="com";
        b.setBasePackage(basePackage1);
        System.out.println(b.getPackages());
        b.setBasePackage(basePackage2);
        System.out.println(b.getPackages());

    }

    @Test
    public void getBasePackage() throws Exception {
        System.out.println(b.getPackages());
    }

    @Test
    public void setWebRoot() throws Exception {

    }

    @Test
    public void setDev() throws Exception {
//        设置开发者模式
        boolean dev1=true;
        b.setDev(dev1);
        System.out.println(b.isDev());
        boolean dev2=false;
        b.setDev(dev2);
        System.out.println(b.isDev());
    }

    @Test
    public void getClassPath() throws Exception {

    }

    @Test
    public void load() throws Exception {

    }

    @Test
    public void config() throws Exception {

    }

    @Test
    public void addScanPackage() throws Exception {
//添加扫描包
        String scanPackage1="com.blade.config1";
        b.addScanPackage(scanPackage1);
        System.out.println(b.getPackages());
        String scanPackage2="com";
        b.addScanPackage(scanPackage2);
        System.out.println(b.getPackages());

    }


    @Test
    public void getPackages() throws Exception {
        System.out.println(b.getPackages());
    }

}