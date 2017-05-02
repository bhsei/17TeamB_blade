package org.Junit.Runwith;

import com.blade.Blade;
import com.blade.ioc.Ioc;
import com.blade.ioc.IocApplication;
import com.blade.ioc.annotation.Inject;
import org.Junit.Runwith.annotation.BladeConfiguration;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created this one by HMH on 2017/5/3.
 */
public class BladeTest4JUnitRunner extends BlockJUnit4ClassRunner {
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public BladeTest4JUnitRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement statement = super.methodInvoker(method, test);
        Blade blade=Blade.$();
        Ioc ioc = blade.ioc();
        IocApplication iocApplication = new IocApplication();
        Class clazz = test.getClass();
        //获取配置参数
        BladeConfiguration annotation = test.getClass().getAnnotation(BladeConfiguration.class);
        String scanPath = annotation.value();
        blade.bConfig().addScanPackage(scanPath);
        try {
            iocApplication.initBeans();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> fieldClass = field.getType();
                    Object value = ioc.getBean(fieldClass);
                    if (value == null) {
                        throw new Exception("ioc初始化失败，字段"+fieldClass.getSimpleName()+"没有可依赖的对象");
                    }
                    field.setAccessible(true);
                    field.set(test, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }
}
