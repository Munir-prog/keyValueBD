package com.mprog.serbuf.utils;


import java.lang.reflect.Method;

public class MethodUtils {

    public static Method getMethod(String prefixType, String field, Class<?> clazz, Class<?> paramType) throws NoSuchMethodException {
        if (paramType != null)
            return clazz.getMethod(prefixType + capitalize(field), paramType);
        else
            return clazz.getMethod(prefixType + capitalize(field));
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
