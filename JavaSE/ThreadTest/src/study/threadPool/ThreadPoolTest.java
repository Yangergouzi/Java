package study.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Executors���Ӧ�ã�
 * 1.�����̶���С���̳߳�
 * 2.���������̳߳�
 * 3.������һ�̳߳أ�ʵ��һ���߳��������Զ��ؽ�һ���̣߳�
 */
public class ThreadPoolTest {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(3);//�����̶���С���̳߳�
		ExecutorService threadPool2 = Executors.newCachedThreadPool();//���������̳߳�
		ExecutorService threadPool3 = Executors.newSingleThreadExecutor();//������һ�̳߳�
		
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
		threadPool.shutdown();//����������ɺ󣬹ر��̳߳�
//		threadPool.shutdownNow();//�����ر��̳߳�
		
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
