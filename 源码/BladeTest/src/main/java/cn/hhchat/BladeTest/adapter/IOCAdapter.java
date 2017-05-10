package cn.hhchat.BladeTest.adapter;

import cn.hhchat.BladeTest.mock.RouteMock;
import cn.hhchat.BladeTest.annotation.BladeConfiguration;
import cn.hhchat.BladeTest.exception.BeanUnAvailableException;
import cn.hhchat.BladeTest.exception.NoConfigurationFindException;
import cn.hhchat.BladeTest.utils.StringUtil;
import com.blade.Blade;
import com.blade.ioc.Ioc;
import com.blade.ioc.IocApplication;
import com.blade.ioc.annotation.Inject;
import com.blade.mvc.route.RouteMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class IOCAdapter {

    final private Logger logger = LoggerFactory.getLogger(IOCAdapter.class);

    public void init(Object testClassObject) throws Exception {

        //获取与Blade框架绑定的Blade对象
        Blade blade = Blade.$();
        //获取IOC
        Ioc ioc = blade.ioc();
        //获取IoC控制应用
        IocApplication iocApplication = new IocApplication();
        //获取测试类
        Class clazz = testClassObject.getClass();
        //获取配置参数
        BladeConfiguration annotation = testClassObject.getClass().getAnnotation(BladeConfiguration.class);
        if (annotation == null) {
            throw new NoConfigurationFindException("没有发现配置注解，请使用BladeConfiguration注入配置");
        }
        String scanPath = annotation.value();
        if (StringUtil.isEmpty(scanPath)) {
            logger.warn("没有配置扫描包，跳过IOC容器初始化");
            return;
        }
        //向Blade框架注入配置
        blade.bConfig().addScanPackage(scanPath);

        //开始初始化配置
        ioc.addBean(RouteMock.class);
        iocApplication.initBeans();

        //初始化Mock
        blade.routeMatcher(new RouteMatcher(blade.routers()));
        ioc.getBean(RouteMock.class).init();

        //向测试类注入Bean
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //向有效的注入字段注入Bean
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> fieldClass = field.getType();
                Object value = ioc.getBean(fieldClass);
                if (value == null) {
                    throw new BeanUnAvailableException("字段" + fieldClass.getSimpleName() + "没有可依赖的对象");
                }
                field.setAccessible(true);
                //注入值
                field.set(testClassObject, value);
            }
        }



    }

    public void clear() {
        Blade.$().ioc().clearAll();
    }
}
