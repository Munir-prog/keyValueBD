package com.mprog.serbuf.utils;

import java.lang.reflect.Field;

public class TypeSelector {

    public static String getType(Field field) {
        String name = field.getType().getName();
        String type;
        if (name.contains(".")) {
            String[] split = name.split("\\.");
            type = split[split.length - 1];
        } else {
            type = name;
        }
        String lowerCaseType = type.toLowerCase();
        return switch (lowerCaseType) {
            case "string" -> "s";
            case "integer", "int" -> "i";
            case "long" -> "l";
            case "double" -> "d";
            case "float" -> "f";
            case "boolean" -> "b";
            default -> throw new RuntimeException("bad type");
        };
    }

    public static Class<?> getTypeClass(String type) {
        return switch (type) {
            case "s" -> String.class;
            case "i" -> Integer.class;
            case "l" -> Long.class;
            case "d" -> Double.class;
            case "f" -> Float.class;
            case "b" -> Boolean.class;
            default -> throw new RuntimeException("bad type");
        };
    }
}
