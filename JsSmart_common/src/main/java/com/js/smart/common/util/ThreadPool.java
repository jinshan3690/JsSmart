package com.js.smart.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具
 *
 */
public class ThreadPool {
	
	/**
	 * 主线程池，自动增长
	 */
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	/**
	 * 定长全程线程池，主要用于优先级不高，频繁加载的后台线程
	 */
	private static ExecutorService backgroundExecutorService = Executors.newFixedThreadPool(20);
	
	public static void threadPool(Runnable runnable) {
		threadPool.submit(runnable);
	}
	
	public static void threadBackgroundPool(Runnable runnable) {
		backgroundExecutorService.submit(runnable);
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
	
}
