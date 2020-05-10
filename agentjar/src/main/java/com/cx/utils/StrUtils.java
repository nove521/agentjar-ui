package com.cx.utils;

public class StrUtils {
    public static String replace(String inString, String oldPattern, String newPattern) {
        if(hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            StringBuilder sb = new StringBuilder();
            int pos = 0;
            int index = inString.indexOf(oldPattern);

            for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
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

    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }
}
