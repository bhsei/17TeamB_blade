package org.hmh.repository;

import com.blade.ioc.annotation.Component;

/**
 * Created this one by HMH on 2017/5/5.
 */
@Component
public class HelloRepository {
    public void sayHello() {
        System.out.println(" ------- say hello in Repository");
    }
}
