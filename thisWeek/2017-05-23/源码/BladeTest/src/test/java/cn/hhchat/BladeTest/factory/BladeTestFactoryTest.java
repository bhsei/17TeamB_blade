package cn.hhchat.BladeTest.factory;

import com.blade.Blade;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created this one by HMH on 2017/5/23.
 */
public class BladeTestFactoryTest {
    @Test
    public void getIocAdapter() throws Exception {
        //确认可以生成
        assert BladeTestFactory.getIocAdapter() != null;
        //确认单例模式
        assert BladeTestFactory.getIocAdapter() == BladeTestFactory.getIocAdapter();
    }

    @Test
    public void getInteceptorAdapter() throws Exception {
        //确认可以生成
        assert BladeTestFactory.getInteceptorAdapter() != null;
        //确认单例模式
        assert BladeTestFactory.getInteceptorAdapter() == BladeTestFactory.getInteceptorAdapter();
    }

}