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

package com.lwh.jackknife.permission.bridge;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestManager {

    private static RequestManager sManager;

    public static RequestManager get() {
        if (sManager == null) {
            synchronized (RequestManager.class) {
                if (sManager == null) {
                    sManager = new RequestManager();
                }
            }
        }
        return sManager;
    }

    private final BlockingQueue<BridgeRequest> mQueue;

    private RequestManager() {
        this.mQueue = new LinkedBlockingQueue<>();

        new RequestExecutor(mQueue).start();
    }

    public void add(BridgeRequest request) {
        mQueue.add(request);
    }
}