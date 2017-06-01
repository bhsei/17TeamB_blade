package com.blade.jdbc.utils;

import org.junit.Test;

/**
 * Created by OovEver on 2017/5/24.
 */
public class StringUtilsTest {
    StringUtils s=new StringUtils();
    @Test
    public void isNotBlank() throws Exception {
//判断string是否为空
        String test1="";
        String test2="abc";
        String test3="132132132";
        String test4="dddd";
        System.out.println(s.isNotBlank(test1));
        System.out.println(s.isNotBlank(test2));
        System.out.println(s.isNotBlank(test3));
        System.out.println(s.isNotBlank(test4));
    }

    @Test
    public void isBlank() throws Exception {
        String test1="";
        String test2="abc";
        String test3="132132132";
        String test4="dddd";
        System.out.println(s.isBlank(test1));
        System.out.println(s.isBlank(test2));
        System.out.println(s.isBlank(test3));
        System.out.println(s.isBlank(test4));
    }

    @Test
    public void trim() throws Exception {
        String test1="   ";
        String test2="   abc";
        String test3=" 132132132";
        String test4="dddd  ";
        String test5="aa   ";
        String test6="  a  a ";
        System.out.print(s.trim(test1));
        System.out.println("aaa");
        System.out.println(s.trim(test2));
        System.out.println(s.trim(test3));
        System.out.print(s.trim(test4));
        System.out.println(s.trim(test5));
        System.out.println(s.trim(test6));

    }

}