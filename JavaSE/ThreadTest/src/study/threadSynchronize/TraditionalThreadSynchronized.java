/*
 * 题目：子线程循环10次，接着主线程循环100次，接着又回到子线程循环10次，接着再到主线程循环100次，如此循环50次
 * */
package study.threadSynchronize;

public class TraditionalThreadSynchronized {

	public static void main(String[] args) {
		TraditionalThreadSynchronized threadSynchronized = new TraditionalThreadSynchronized();
		threadSynchronized.init();
	}
	public void init() {
		Business business = new Business();
		new Thread(new Runnable() {			
			@Override
			public void run() {	
				for(int i = 1;i <= 50;i++) {
					business.sub(i);
				}
			}
		}).start();
		for(int i = 1;i <= 50;i++) {
			business.main(i);
		}
	}
	
	class Business{
		boolean subRun = true;
		public synchronized void sub(int i) {
			if(!subRun) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int j = 1;j <= 10;j++) {
				System.out.println("sub thread : " + j + ",loop of " + i);
				}
			subRun = false;
			this.notify();
		}
		
		public synchronized void main(int i) {
			if(subRun) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int j = 1;j <= 100;j++) {
				System.out.println("main  thread : " + j + ",loop of " + i);
				}
				subRun = true;
				this.notify();
		}			
	}
}
