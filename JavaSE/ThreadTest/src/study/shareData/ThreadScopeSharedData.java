package study.shareData;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ThreadScopeSharedData {
	private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				int data = new Random().nextInt(999);
				threadData.put(Thread.currentThread(), data);
				System.out.println(Thread.currentThread().getName() + " set data: " + threadData.get(Thread.currentThread()));
				new A().getThreadData();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int data = new Random().nextInt(999);
				threadData.put(Thread.currentThread(), data);
				System.out.println(Thread.currentThread().getName() + " set data: " + threadData.get(Thread.currentThread()));
				new A().getThreadData();
			}
		}).start();
	}
	
	static class A{
		public void getThreadData() {
			System.out.println(Thread.currentThread().getName() + "has get data :" + threadData.get(Thread.currentThread()));
		}
	}
}
