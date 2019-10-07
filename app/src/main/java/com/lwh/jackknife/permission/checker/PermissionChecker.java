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

package com.lwh.jackknife.permission.checker;

import android.content.Context;

import java.util.List;

public interface PermissionChecker {

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context {@link Context}.
     * @param permissions one or more permissions.
     *
     * @return true, other wise is false.
     */
    boolean hasPermission(Context context, String... permissions);

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context {@link Context}.
     * @param permissions one or more permissions.
     *
     * @return true, other wise is false.
     */
    boolean hasPermission(Context context, List<String> permissions);

}