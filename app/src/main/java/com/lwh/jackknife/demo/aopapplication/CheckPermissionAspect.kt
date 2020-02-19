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
package com.lwh.jackknife.demo.aopapplication

import android.app.Activity
import android.util.Log
import com.lwh.jackknife.permission.Action
import com.lwh.jackknife.permission.XPermission
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

@Aspect
class CheckPermissionAspect {
    @Pointcut("execution(@com.lwh.jackknife.demo.aopapplication.Permission * *(..)) && @annotation(permission)")
    fun checkPermission(permission: Permission?) {
    }

    @Around("checkPermission(permission)")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint, permission: Permission) {
        val activity = joinPoint.target as Activity
        Log.e("aop", activity.javaClass.name)
        if (XPermission.hasPermissions(activity, *permission.value)) {
            joinPoint.proceed() //获得权限，执行原方法
        } else {
            XPermission.with(activity)
                    .runtime()
                    .permission(*permission.value)
                    .onGranted(Action<List<String?>?> {
                        try {
                            joinPoint.proceed() //获得权限，执行原方法
                        } catch (throwable: Throwable) {
                            throwable.printStackTrace()
                        }
                    })
                    .onDenied(Action<List<String?>?> { permissions ->
                        if (XPermission.hasAlwaysDeniedPermission(activity, permissions)) { // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XPermission.with(activity).runtime().setting().start(REQUEST_CODE_SETTING)
                        }
                    })
                    .start()
        }
    }

    companion object {
        private const val REQUEST_CODE_SETTING = 0x01
    }
}