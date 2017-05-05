package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import org.hmh.repository.HelloRepository;

/**
 * Created this one by HMH on 2017/5/2.
 */
@Service
public class HelloService {

    @Inject
    HelloRepository repository;

    public void sayHello() {
        System.out.println(" ----- Hello before in Service");
        repository.sayHello();
        System.out.println(" ----- Hello after in Service");
    }
}
