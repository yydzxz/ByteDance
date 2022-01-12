package com.github.yydzxz.common.util;

/**
 * @author yangyidian
 * @date 2021/09/02
 **/
public class ByteDanceAppIdHolder {
    private final static ThreadLocal<String> HOLDER = ThreadLocal.withInitial(() -> "");

    public static String get() {
        return HOLDER.get();
    }

    public static void set(String value) {
        HOLDER.set(value);
    }

    public static void remove() {
        HOLDER.remove();
    }

}
