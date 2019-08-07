/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
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
package com.js.smart.common.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.js.smart.common.app.BaseActivityI;

import java.util.Stack;


public class AcStack {
    private static Stack<BaseActivityI> activityStack;
    private static final AcStack instance = new AcStack();

    private AcStack() {
    }

    public static AcStack create() {
        return instance;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return activityStack.size();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(BaseActivityI activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public AppCompatActivity topActivity() {
        if (activityStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend KJActivity");
        }
        if (activityStack.isEmpty()) {
            return null;
        }
        BaseActivityI activity = activityStack.lastElement();
        return (AppCompatActivity) activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public AppCompatActivity findActivity(Class<?> cls) {
        BaseActivityI activity = null;
        for (BaseActivityI aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return (AppCompatActivity) activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        BaseActivityI activity = activityStack.lastElement();
        finishActivity((AppCompatActivity) activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
             activity.finish();//此处不用finish
        }
    }

    public void removeActivity(AppCompatActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (BaseActivityI activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity((AppCompatActivity) activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (BaseActivityI activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity((AppCompatActivity) activity);
            }
        }
    }

    public void finishOthersActivity(String className) {
        for (BaseActivityI activity : activityStack) {
            if (!(activity.getClass().getSimpleName().equals(className))) {
                finishActivity((AppCompatActivity) activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                ((AppCompatActivity) activityStack.get(i)).finish();
            }
        }
        activityStack.clear();
    }

    @Deprecated
    public void AppExit(Context cxt) {
        appExit(cxt);
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}