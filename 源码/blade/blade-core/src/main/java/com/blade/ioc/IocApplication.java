/**
 * Copyright (c) 2015, biezhi 王爵 (biezhi.me@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blade.ioc;

import com.blade.Blade;
import com.blade.comparator.OrderComparator;
import com.blade.context.WebContextListener;
import com.blade.ioc.annotation.Component;
import com.blade.ioc.annotation.Service;
import com.blade.kit.CollectionKit;
import com.blade.kit.IocKit;
import com.blade.kit.reflect.ReflectKit;
import com.blade.kit.resource.ClassInfo;
import com.blade.kit.resource.ClassReader;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.RestController;
import com.blade.mvc.context.DynamicContext;
import com.blade.mvc.interceptor.Interceptor;
import com.blade.mvc.route.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

/**
 * IOC container, used to initialize the IOC object
 *
 * @author <a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since 1.0
 */
public final class IocApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocApplication.class);

    /**
     * aop interceptor
     */
    private static List<Object> aopInterceptors = CollectionKit.newArrayList(8);

    /**
     * 属性
     * blade
     * comparator：排序
     * ctxs：上下文列表
     */
    private Blade blade;
    private OrderComparator orderComparator;
    private List<WebContextListener> ctxs = CollectionKit.newArrayList();

    /**
     * 构造方法
     * 初始化blade
     * 初始化orderomparator
     */
    public IocApplication() {
        this.blade = Blade.$();
        this.orderComparator = new OrderComparator();
    }

    /**
     * 初始化Beans
     * @throws Exception
     */
    public void initBeans() throws Exception {
//      获取设置的包名
        Set<String> pkgs = blade.bConfig().getPackages();
        if (null != pkgs) {
//      如果不为空
//          获取Ioc对象
            Ioc ioc = blade.ioc();
//          获取Builder对象
            RouteBuilder routeBuilder = blade.routeBuilder();
//          初始化处理器集合
            List<BeanProcessor> processors = CollectionKit.newArrayList();
//          初始化上下文类集合
            List<ClassInfo> ctxClasses = CollectionKit.newArrayList(8);
//          初始化类信息列表
            List<ClassInfo> processoers = CollectionKit.newArrayList(8);
//          对于每一个包做操作
            pkgs.forEach(p -> {
//              加载类
                ClassReader classReader = DynamicContext.getClassReader(p);
//              同时解析类
                Set<ClassInfo> classInfos = classReader.getClass(p, true);
//              processors如果信息不为空
                if (null != classInfos) {
//                  对于每一个类
                    classInfos.forEach(c -> {
//                      获取类别
                        Class<?> clazz = c.getClazz();
//                      如果类不是接口或者抽象类
                        if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
//                          获取注解
                            Service service = clazz.getAnnotation(Service.class);
                            Controller controller = clazz.getAnnotation(Controller.class);
                            RestController restController = clazz.getAnnotation(RestController.class);
                            Component component = clazz.getAnnotation(Component.class);
                            if (null != service || null != component) {
//                          如果不是service也不是component
//                              按Class的类别注册
                                ioc.addBean(clazz);
                            } else if (null != controller || null != restController) {
//                              如果不是controller也不是restController
                                ioc.addBean(clazz);
//                              注册路由？？？
                                routeBuilder.addRouter(clazz);
                            } else if (clazz.getSuperclass().getName().equals("com.blade.aop.AbstractMethodInterceptor")) {
//                              如果是抽象的aop拦截器，添加clazz实例
                                aopInterceptors.add(ReflectKit.newInstance(clazz));
                            } else {
//                              如果是普通接口
                                Class<?>[] interfaces = clazz.getInterfaces();
//                              对于所有的继承的接口
                                for (Class<?> in : interfaces) {
                                    if (in.equals(Interceptor.class)) {
//                                      如果是拦截器类型
                                        ioc.addBean(clazz);
//                                      注册路由
                                        routeBuilder.addInterceptor(clazz);
                                    } else if (in.equals(WebContextListener.class)) {
//                                      如果是上下文监听器，添加到上下文里
                                        ctxClasses.add(c);
                                    } else if (in.equals(BeanProcessor.class)) {
//                                      如果是bean处理器，添加处理器
                                        processoers.add(c);
                                    }
                                }
                            }
                        }
                    });
                }
            });
//          排序
            ctxClasses.sort(orderComparator);
            processoers.sort(orderComparator);
//          对于上下文，注册所有的类，并实例化
            ctxClasses.forEach(c -> {
                Object bean = ioc.addBean(c.getClazz());
                ctxs.add((WebContextListener) bean);
            });
//          所有的类信息，注册类并实例化
            processoers.forEach(c -> {
                Object bean = ioc.addBean(c.getClazz());
                processors.add((BeanProcessor) bean);
            });
//          注册所有的处理器注册
            processors.forEach(b -> b.register(ioc));
//          如果ioc里面不为空
            if (null != ioc.getBeans() && !ioc.getBeans().isEmpty()) {
//              输出所有的beans看看
                LOGGER.info("Add Object: {}", ioc.getBeans());
            }
//          获取所有的beanDefines信息
            List<BeanDefine> beanDefines = ioc.getBeanDefines();
            if (null != beanDefines) {
//              将beanDefines注入
                beanDefines.forEach(b -> IocKit.injection(ioc, b));
            }

        }
    }

    public void initCtx(ServletContext sec) {
        ctxs.forEach(c -> c.init(blade.bConfig(), sec));
    }

    public static List<Object> getAopInterceptors() {
        return aopInterceptors;
    }

}