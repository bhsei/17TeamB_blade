package com.blade.jdbc.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OovEver on 2017/5/24.
 */
public class CollectionUtilsTest {
    CollectionUtils c=new CollectionUtils();
    @Test
    public void isEmpty() throws Exception {
        List<Integer> ls=new ArrayList<>();
        List<Integer>ls1=new ArrayList<>();
        ls1.add(1);
        ls1.add(2);
        System.out.println(c.isEmpty(ls));
        System.out.println(c.isEmpty(ls1));
    }

}