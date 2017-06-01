package com.blade.jdbc.utils;

import org.junit.Test;

/**
 * Created by OovEver on 2017/5/24.
 */
public class NameUtilsTest {
    NameUtils n=new NameUtils();
    @Test
    public void getFirstUpperName() throws Exception {
        String test1="";
        String test2="abc";
        String test3="132132132";
        String test4="dddd";
        String test5="Dddd";
        System.out.println(n.getFirstUpperName(test1));
        System.out.println(n.getFirstUpperName(test2));
        System.out.println(n.getFirstUpperName(test3));
        System.out.println(n.getFirstUpperName(test4));
        System.out.println(n.getFirstUpperName(test5));
    }

    @Test
    public void getFirstLowerName() throws Exception {
        String test1="";
        String test2="Abc";
        String test3="132132132";
        String test4="dddd";
        String test5="Dddd";
        System.out.println(n.getFirstLowerName(test1));
        System.out.println(n.getFirstLowerName(test2));
        System.out.println(n.getFirstLowerName(test3));
        System.out.println(n.getFirstLowerName(test4));
        System.out.println(n.getFirstLowerName(test5));


    }

    @Test
    public void getCamelName() throws Exception {
        String test1="";
        String test2="A_b_c";
        String test3="13_213_2132";
        String test4="dd_dd";
        String test5="Dd_d_d";
        System.out.println(n.getCamelName(test1));
        System.out.println(n.getCamelName(test2));
        System.out.println(n.getCamelName(test3));
        System.out.println(n.getCamelName(test4));
        System.out.println(n.getCamelName(test5));

    }

    @Test
    public void getUnderlineName() throws Exception {
        String test1=null;
        String test2 = "aBC";
        String test3 = "132132132";
        String test4 = "ddDd";
        String test5 = "ddDD";
        System.out.println(n.getUnderlineName(test1));
        System.out.println(n.getUnderlineName(test2));
        System.out.println(n.getUnderlineName(test3));
        System.out.println(n.getUnderlineName(test4));
        System.out.println(n.getUnderlineName(test5));
    }

    @Test
    public void createUniqueFileName() throws Exception {
        String test1=null;//文件为空异常
        String test2 = "aBC";//文件名无后缀异常
        String test3 = "132132132.png";
        String test4 = "ddDd.txt";
        String test5 = "ddDd.txt";//文件名无后缀异常
        String test6 = "aBC.txt";
        String test7 = "aBC.jpg";
//        System.out.println(n.createUniqueFileName(test1));
//        System.out.println(n.createUniqueFileName(test2));
//        System.out.println(n.createUniqueFileName(test3));
        System.out.println(n.createUniqueFileName(test4));
        System.out.println(n.createUniqueFileName(test5));
//        System.out.println(n.createUniqueFileName(test6));
//        System.out.println(n.createUniqueFileName(test7));


    }

    @Test
    public void createEndSuffixFileName() throws Exception {
        String test1=null;//文件为空异常
        String test2 = "aBC.";//文件名无后缀异常
        String test3 = "132132132.png";
        String test4 = "ddDd.txt";
        String test5 = "ddDD";//文件名无后缀异常
        String test6 = "aBC.txt";
        String test7 = "aBC.jpg";
        System.out.println(n.createEndSuffixFileName(test2,".txt"));
//        System.out.println(n.createEndSuffixFileName(test3,".txt"));
//        System.out.println(n.createEndSuffixFileName(test4,"png"));
//        System.out.println(n.createEndSuffixFileName(test6,".txt"));
//        System.out.println(n.createEndSuffixFileName(test7,".txt"));
    }

}