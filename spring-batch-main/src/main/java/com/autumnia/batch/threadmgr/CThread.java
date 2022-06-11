package com.autumnia.batch.threadmgr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

public class CThread {
	private int THREAD_COUNT;
	private String THREAD_NAME ;	
	
	private ExecutorService threadPoolExecutor;
	private List<Callable<Boolean>> tasks;
	private List<Future<Boolean>> futures;

	public CThread(String threadName, int threadCount) {
		this(threadName);
		this.THREAD_COUNT = threadCount;
	}	

	public CThread(String threadName) {
		this.THREAD_NAME = threadName.trim().toLowerCase();
	}		

	public void run() {
		threadPoolExecutor = Executors.newFixedThreadPool( THREAD_COUNT );
		tasks = new ArrayList<Callable<Boolean>>();
	}

	public void setTask(Callable<Boolean> task) {
		this.tasks.add(task);
	}
	
	public void working() throws Exception {
		this.futures = this.threadPoolExecutor.invokeAll(this.tasks);
		for (Future<Boolean> result : this.futures) {
			 result.get() ;
		}
	}
	
	public void stop() throws Exception {
		this.tasks.clear();
		this.futures.clear();
		
		Thread.sleep(1);
		
		this.threadPoolExecutor.shutdown();
		
		while (!this.threadPoolExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
			this.threadPoolExecutor.shutdownNow();
		}		
	}
	
	
}
