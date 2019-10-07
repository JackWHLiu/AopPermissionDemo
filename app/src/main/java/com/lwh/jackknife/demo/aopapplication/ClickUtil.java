package com.lwh.jackknife.demo.aopapplication;

public class ClickUtil {
    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        long currentClickTime = System.currentTimeMillis();
        boolean flag = currentClickTime - lastClickTime<MIN_DELAY_TIME;
        lastClickTime = currentClickTime;
        return flag;
    }
}
