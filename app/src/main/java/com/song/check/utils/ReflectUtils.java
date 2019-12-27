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
            // ClassLoader cl = context.getClassLoader();
            ClassLoader cl = ClassLoader.getSystemClassLoader();
//            Class<?> systemProperties = cl.loadClass("android.os.SystemProperties");
            Class<?> systemProperties = cl.loadClass("android.os.Build");
            Method method = systemProperties.getMethod("getString", String.class);
            method.setAccessible(true);
            Object[] params = new Object[1];
            params[0] = property;
            return (String) method.invoke(systemProperties, params);
        } catch (Exception e) {
            return Constants.UNKNOWN;
        }
    }
}
