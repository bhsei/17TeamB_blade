package cn.hhchat.BladeTest.interceptor;

/**
 * Created this one by HMH on 2017/5/5.
 */
public interface Interceptor {

    void before() throws Throwable;

    void after(Interceptor interceptor);
}
