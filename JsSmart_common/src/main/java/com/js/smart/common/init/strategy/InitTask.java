package com.js.smart.common.init.strategy;

import android.content.Context;

import retrofit2.Retrofit;

/** 
 * “初始化策略” 接口 ， 每个“初始化策略”实现这个接口，
 * 并在init方法中写具体初始化逻辑
 *
 */
public interface InitTask {
	
	 void init(Context context, Retrofit retrofit);
	
}
