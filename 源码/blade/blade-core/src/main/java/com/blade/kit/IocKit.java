/**
 * Copyright (c) 2016, biezhi 王爵 (biezhi.me@gmail.com)
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
package com.blade.kit;

import com.blade.ioc.BeanDefine;
import com.blade.ioc.FieldInjector;
import com.blade.ioc.Ioc;
import com.blade.ioc.annotation.InjectWith;
import com.blade.kit.reflect.ClassDefine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * IocKit, get Bean
 *
 * @author    <a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since 1.5
 */
public class IocKit {

    /**
     * Get bean according to BeanDefine
     *
     * @param beanDefine    beandefine object
     * @return bean object
     */
    public static Object getBean(BeanDefine beanDefine) {
        Object bean = beanDefine.getBean();
        return bean;
    }

    public static void injection(Ioc ioc, BeanDefine beanDefine) {
        ClassDefine classDefine = ClassDefine.create(beanDefine.getType());
        List<FieldInjector> fieldInjectors = IocKit.getInjectFields(ioc, classDefine);
        Object bean = beanDefine.getBean();
        for (FieldInjector fieldInjector : fieldInjectors) {
            fieldInjector.injection(bean);
        }
    }

    /**
     * Get @Inject Annotated field
     *
     * @param ioc         ioc container
     * @param classDefine classDefine
     * @return return FieldInjector
     */
    public static List<FieldInjector> getInjectFields(Ioc ioc, ClassDefine classDefine) {
        List<FieldInjector> injectors = new ArrayList<FieldInjector>(8);
        for (Field field : classDefine.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                //获取injectwith注解的field
                InjectWith with = annotation.annotationType().getAnnotation(InjectWith.class);
                if (with != null) {
//                  添加注入器
                    injectors.add(new FieldInjector(ioc, field));
                }
            }
        }
        if (injectors.size() == 0) {
//          返回空
            return Collections.emptyList();
        }
//      返回注入器
        return injectors;
    }

}