package com.song.check.utils;

import java.lang.reflect.Method;

public class ReflectUtils {

    /**
     * 获取系统属性方法
     *
     * @param property
     * @return
     */
    public static String getProperty(String property) {
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            Class<?> systemProperties = cl.loadClass("android.os.SystemProperties");
            Method method = systemProperties.getMethod("get", String.class);
            method.setAccessible(true);
            Object[] params = new Object[1];
            params[0] = property;
            return (String) method.invoke(systemProperties, params);
        } catch (Exception e) {
            return Constants.UNKNOWN;
        }
    }
}
