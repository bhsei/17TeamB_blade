package cn.hhchat.BladeTest.adapter;

import cn.hhchat.BladeTest.annotation.BladeConfiguration;
import cn.hhchat.BladeTest.annotation.InterceptorClasses;
import cn.hhchat.BladeTest.interceptor.TimeInterceptor;
import cn.hhchat.BladeTest.runner.BladeTest4JUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created this one by HMH on 2017/5/23.
 */
@RunWith(BladeTest4JUnitRunner.class)
@BladeConfiguration("cn")
@InterceptorClasses({TimeInterceptor.class})
public class InteceptorAdapterTest {
    //测试拦截器初始化
    @Test
    public void init() throws Exception {
        //该模块进行功能测试
    }

    @Test
    public void clear() throws Exception {

    }

}