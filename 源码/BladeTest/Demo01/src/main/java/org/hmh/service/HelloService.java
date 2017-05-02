package org.hmh.service;

import com.blade.ioc.annotation.Service;

/**
 * Created this one by HMH on 2017/5/2.
 */
@Service
public class HelloService {

    public void sayHello() {
        System.out.println("Hello");
    }
}
