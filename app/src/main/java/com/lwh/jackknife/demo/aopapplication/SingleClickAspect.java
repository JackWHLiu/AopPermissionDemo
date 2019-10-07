package com.lwh.jackknife.demo.aopapplication;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Calendar;

/**
 * 能过注解@SingleClick aop切片的方式在编译期间织入源代码中
 * 功能: 防止二次点击
 */
@Aspect
public class SingleClickAspect {

    @Around("execution(@com.lwh.jackknife.demo.aopapplication.SingleClick * *(..))")
    public void aroundPointMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        Log.e("aop", "================"+Calendar.getInstance().getTimeInMillis()+"=====================");
        if (ClickUtil.isFastClick()){
            Log.e("aop", "=======点击太快了========="+Calendar.getInstance().getTimeInMillis()+"=====================");
            return;
        }
        Log.e("aop", "================"+Calendar.getInstance().getTimeInMillis()+"=====================");
        joinPoint.proceed();//执行原方法
//        View view = null;
//        for (Object arg : joinPoint.getArgs())
//            if (arg instanceof View) view = (View) arg;
//        if (view != null) {
//            Object tag = view.getTag(TIME_TAG);
//            long lastClickTime = ((tag != null) ? (long) tag : 0);
//            long currentTime = Calendar.getInstance().getTimeInMillis();
//            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
//                view.setTag(TIME_TAG, currentTime);
//                joinPoint.proceed();//执行原方法
//            }
//        }

    }
}
