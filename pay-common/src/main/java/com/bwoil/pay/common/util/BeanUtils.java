package com.bwoil.pay.common.util;

import com.bwoil.common.framework.util.ReflectionUtils;
import lombok.extern.apachecommons.CommonsLog;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Map;
@CommonsLog
public class BeanUtils {

    public static void copy(Object bean, Map<String,Object> map){

        Class<?> beanClass = bean.getClass();
        Field[] viewFields = ReflectionUtils.getAllDeclaredFields(beanClass);
        for (Field viewField : viewFields) {
            try {
                String name = viewField.getName();
                if(!map.containsKey(name)){
                    continue;
                }
                viewField.setAccessible(true);
                PropertyDescriptor beanProperty = ReflectionUtils.findProperty(beanClass, name);
                if (beanProperty == null) {
                    continue;
                }
                if (beanProperty.getWriteMethod() == null) {
                    continue;
                }

               Object value= map.get(name);
                if(value!=null) {
                    beanProperty.getWriteMethod().invoke(bean, value.toString());
                }
            } catch (Exception e) {
                log.error("读取加密字段报错", e);
                continue;

            }
        }
    }
}
