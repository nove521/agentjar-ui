package com.cx.utils;

import com.google.gson.Gson;

import java.util.*;

public class StrUtils {

    public static final String BLANK_STR = "";

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            StringBuilder sb = new StringBuilder();
            int pos = 0;
            int index = inString.indexOf(oldPattern);

            for (int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sb.append(inString.substring(pos, index));
                sb.append(newPattern);
                pos = index + patLen;
            }

            sb.append(inString.substring(pos));
            return sb.toString();
        } else {
            return inString;
        }
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    private static final Gson gson = new Gson();

    public static Gson json() {
        return gson;
    }

    public static <T> T changeTo(String str, Class<T> clazz) {
        if (Objects.isNull(str)) {
            return null;
        }

        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return (T) Integer.valueOf(str);
        }
        if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return (T) Double.valueOf(str);
        }
        if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            return (T) Float.valueOf(str);
        }
        if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            return (T) Byte.valueOf(str);
        }
        if (clazz.equals(String.class)) {
            return (T) str;
        }
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return (T) Boolean.valueOf(str);
        }
        if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return (T) Long.valueOf(str);
        }
        if (clazz.equals(Map.class)) {
            return json().fromJson(str, clazz);
        }
        if (Iterable.class.isAssignableFrom(clazz)) {
            return json().fromJson(str, clazz);
        }

        return null;
    }

    /**
     * 在某个位置插入值返回新字符串
     *
     * @param strArr  被切割的字符串数组
     * @param index   插入的位置 0 代表开头，之后会后移
     * @param insert  插入字符串
     * @param joinStr 要连接的字符串
     * @return 返回新字符串
     */
    public static String insertStrArr(String[] strArr, int index, String insert, String joinStr) {
        int len = strArr.length;
        if (index > len) {
            return BLANK_STR;
        }
        String[] newStrArr = new String[len + 1];
        System.arraycopy(strArr, 0, newStrArr, 0, index);
        newStrArr[index] = insert;
        System.arraycopy(strArr, index, newStrArr, index + 1, len - index);
        return String.join(joinStr, newStrArr);
    }
}
