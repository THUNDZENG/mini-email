package io.github.thundzeng.miniemail.util;

import com.sun.istack.internal.Nullable;

/**
 * 字符串工具类
 *
 * @author thundzeng
 */
public class StringUtils {
    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }
}
