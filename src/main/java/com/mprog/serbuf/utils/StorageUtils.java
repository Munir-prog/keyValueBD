package com.mprog.serbuf.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class StorageUtils {


    private static final List<Character> letters;
    private static final List<Integer> digits;

    public static String getStoragePrefix(String key) {
        char first = key.charAt(0);
        if (letters.contains(first)) {
            return Character.toString(first);
        }
        int intVal = Character.getNumericValue(first);
        if (digits.contains(intVal)) {
            return "m" + intVal;
        }
        return "other";
    }

    static {
        letters = Arrays.stream("abcdefghijklmnopqrstuvwxyz".split(""))
                .map(it -> it.charAt(0))
                .toList();
        digits = Arrays.stream("1234567890".split(""))
                .map(Integer::parseInt)
                .toList();
    }
}
