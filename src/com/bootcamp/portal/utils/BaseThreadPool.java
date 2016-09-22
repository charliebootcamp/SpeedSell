package com.bootcamp.portal.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.bootcamp.portal.UserSettings;

public class BaseThreadPool {

	private ThreadPoolExecutor executor;

	public BaseThreadPool() {
		super();
		executor = new ThreadPoolExecutor(UserSettings.getThreadCoreSize(),
				UserSettings.getThreadMaxSize(),
				UserSettings.getThreadAliveTime(), TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public void execute(Runnable runnable) {
		executor.execute(runnable);
	}
}
