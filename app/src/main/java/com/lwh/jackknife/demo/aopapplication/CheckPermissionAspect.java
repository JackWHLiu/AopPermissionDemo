/*
 * Copyright (C) 2019 The JackKnife Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lwh.jackknife.demo.aopapplication;

import android.app.Activity;
import android.util.Log;

import com.lwh.jackknife.permission.Action;
import com.lwh.jackknife.permission.XPermission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

@Aspect
public class CheckPermissionAspect {

    private static final int REQUEST_CODE_SETTING = 0x01;

    @Pointcut("execution(@com.lwh.jackknife.demo.aopapplication.Permission * *(..)) && @annotation(permission)")
    public void checkPermission(Permission permission) {
    }

    @Around("checkPermission(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable {
        final Activity activity = (Activity) joinPoint.getTarget();
        Log.e("aop", activity.getClass().getName());
        if (XPermission.hasPermissions(activity, permission.value())) {
            joinPoint.proceed();//获得权限，执行原方法
        } else {
            XPermission.with(activity)
                .runtime()
                .permission(permission.value())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        try {
                            joinPoint.proceed();//获得权限，执行原方法
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (XPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XPermission.with(activity).runtime().setting().start(REQUEST_CODE_SETTING);
                        }
                    }
                })
                .start();
        }
    }
}
