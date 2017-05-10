package cn.hhchat.BladeTest.adapter;

import cn.hhchat.BladeTest.annotation.InterceptorClasses;
import cn.hhchat.BladeTest.exception.InterceptorError;
import cn.hhchat.BladeTest.interceptor.Interceptor;
import cn.hhchat.BladeTest.Statement.InterceptorStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created this one by HMH on 2017/5/5.
 */

public class InteceptorAdapter {

    final private Logger logger = LoggerFactory.getLogger(InteceptorAdapter.class);

    public void init(InterceptorStatement statement, Object testClassObject) throws InterceptorError {
        //添加拦截器
        //获取注解中配置的拦截类
        InterceptorClasses annotation = testClassObject.getClass().getAnnotation(InterceptorClasses.class);
        if (annotation == null) {
            return ;
        }
        Class<?>[] interceptorClasses = annotation.value();
        try
        {
            for (Class<?> cls : interceptorClasses)
            {
                statement.addInterceptor((Interceptor)cls.newInstance());
            }
        }
        catch (Exception e)
        {
            throw new InterceptorError(e.getMessage());
        }
    }

    public void clear() {

    }
}
