/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.android.systemui.process.condition;

import com.android.systemui.dagger.qualifiers.Application;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.shared.condition.Condition;

import kotlinx.coroutines.CoroutineScope;

import javax.inject.Inject;

/**
 * {@link SystemProcessCondition} checks to make sure the current process is being ran by the
 * System User.
 */
public class SystemProcessCondition extends Condition {
    private final ProcessWrapper mProcessWrapper;

    @Inject
    public SystemProcessCondition(@Application CoroutineScope scope,
            ProcessWrapper processWrapper) {
        super(scope);
        mProcessWrapper = processWrapper;
    }

    @Override
    protected void start() {
        updateCondition(mProcessWrapper.isSystemUser());
    }

    @Override
    protected void stop() {
    }

    @Override
    protected int getStartStrategy() {
        return START_EAGERLY;
    }
}
