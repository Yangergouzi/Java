package study.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Executors类的应用：
 * 1.创建固定大小的线程池
 * 2.创建缓存线程池
 * 3.创建单一线程池（实现一个线程死掉后自动重建一个线程）
 */
public class ThreadPoolTest {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(3);//创建固定大小的线程池
		ExecutorService threadPool2 = Executors.newCachedThreadPool();//创建缓存线程池
		ExecutorService threadPool3 = Executors.newSingleThreadExecutor();//创建单一线程池
		
		for(int i=1; i<=10 ; i++) {
			final int task = i;
			threadPool.execute(new Runnable() {	
		//	threadPool2.execute(new Runnable() {		
		//  threadPool3.execute(new Runnable() {	
				@Override
				public void run() {
					for(int j=1 ; j<=10 ; j++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " loop of " + j + " execute task " + task);
					}
				}
			});
		}
		System.out.println("all tasks has published!");
		threadPool.shutdown();//所有任务完成后，关闭线程池
//		threadPool.shutdownNow();//立即关闭线程池
		
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);		
		
		scheduledThreadPool.schedule(
			new Runnable() {		
			@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " booming!");
					}
			}, 
			5, 
			TimeUnit.SECONDS);
		
		scheduledThreadPool.scheduleAtFixedRate(
			new Runnable() {		
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " booming!...");
					}
				},
			8, 
			2, 
			TimeUnit.SECONDS); 
	}
}
