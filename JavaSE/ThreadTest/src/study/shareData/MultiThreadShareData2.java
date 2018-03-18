package study.shareData;
/*如果每个线程执行同一段代码，可以用同一个Runable，共享数据在Runable里面（卖票系统）
 *如果每个线程执行代码不同，多个线程间共享数据有两种方式：
 * 1.将共享数据封装在一个对象中，然后逐一传递给各个Runable对象
 * 2.将各个Runable对象作为内部类，共享数据作为外部类的成员
 * 
 * 设计4个线程，其中两个线程每次对i加一，另外两个每次对i减一
 */
public class MultiThreadShareData2 {
//	private static ShareData data2 = new ShareData();
	public static void main(String[] args) {	
		//方法1
		ShareData data1 = new ShareData();
		for(int t=0;t<2;t++) {
			MyRunable1 myRunable1 = new MyRunable1(data1);
			MyRunable2 myRunable2 = new MyRunable2(data1);
			new Thread(myRunable1).start();
			new Thread(myRunable2).start();
		}
		
		//方法2
	/*	for(int t=0;t<2;t++) {
			new Thread(new Runnable() {		
				@Override
				public void run() {
					for(int c=0;c<10;c++) {
						data2.increment();
					}
				}
			}).start();
			
			new Thread(new Runnable() {		
				@Override
				public void run() {
					for(int c=0;c<10;c++) {
						data2.decrement();
					}
				}
			}).start();
		}*/
	}
	
	static class MyRunable1 implements Runnable{
		ShareData data = new ShareData();
		public MyRunable1(ShareData data) {
			this.data = data;
		}
		@Override
		public void run() {
			for(int c=0;c<10;c++) {
				data.increment();
			}
		}
	}
	static class MyRunable2 implements Runnable{
		ShareData data = new ShareData();
		public MyRunable2(ShareData data) {
			this.data = data;
		}
		@Override
		public void run() {
			for(int c=0;c<10;c++) {
				data.decrement();
			}
		}
	}
	
	static class  ShareData {
		int i = 0;
		
		public synchronized void increment() {
			i++;
			System.out.println(Thread.currentThread().getName() + " inc : " + i);
		}
		
		public synchronized void decrement() {
			i--;
			System.out.println(Thread.currentThread().getName() + " dec : " + i);
		}

	}
}
