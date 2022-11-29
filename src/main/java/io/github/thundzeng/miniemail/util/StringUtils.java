package io.github.thundzeng.miniemail.util;


/**
 * 字符串工具类
 *
 * @author thundzeng
 */
public class StringUtils {
    public static boolean isEmpty(Object str) {
        if (null == str) {
            return true;
        }
        String toString = str.toString();
        return ("".equals(toString) || "".equals(toString.trim()));
    }
}
