/*
 * ��Ŀ�����߳�ѭ��10�Σ��������߳�ѭ��100�Σ������ֻص����߳�ѭ��10�Σ������ٵ����߳�ѭ��100�Σ����ѭ��50��
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
