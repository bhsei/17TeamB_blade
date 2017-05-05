package cn.hhchat.BladeTest.annotation;

import cn.hhchat.BladeTest.interceptor.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created this one by HMH on 2017/5/5.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptorClasses {
    public Class<? extends Interceptor>[] value();
}
