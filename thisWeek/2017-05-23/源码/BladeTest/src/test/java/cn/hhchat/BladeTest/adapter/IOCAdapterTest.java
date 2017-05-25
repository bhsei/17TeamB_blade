package cn.hhchat.BladeTest.adapter;

import cn.hhchat.BladeTest.annotation.BladeConfiguration;
import cn.hhchat.BladeTest.annotation.InterceptorClasses;
import cn.hhchat.BladeTest.interceptor.TimeInterceptor;
import com.blade.Blade;
import com.blade.ioc.Ioc;
import com.blade.ioc.annotation.Inject;
import org.hmh.controller.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created this one by HMH on 2017/5/23.
 */
@BladeConfiguration("org.hmh")
public class IOCAdapterTest {

    @Inject
    private
    HelloController helloController;

    /**
     * 确认配置正确，初始化能够成功
     * @throws Exception 初始化异常
     */
    @Test
    public void initErr() throws Exception {
        //确认初始化是否存在异常
        IOCAdapter iocAdapter = new IOCAdapter();
        iocAdapter.init(this);
        //确认初始化结果
        assert helloController != null;
    }

    @Test
    public void clear() throws Exception {
        //确认初始化是否存在异常
        IOCAdapter iocAdapter = new IOCAdapter();
        iocAdapter.init(this);
        iocAdapter.clear();
        //确认ioc容器中数量
        assert Blade.$().ioc().getBeans().size() == 0;
    }

}