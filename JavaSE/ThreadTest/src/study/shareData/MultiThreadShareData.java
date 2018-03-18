package study.shareData;
/*如果每个线程执行同一段代码，可以用同一个Runable，共享数据在Runable里面（卖票系统）
 *如果每个线程执行代码不同，多个线程间共享数据有两种方式：
 * 1.将共享数据封装在一个对象中，然后逐一传递给各个Runable对象
 * 2.将各个Runable对象作为内部类，共享数据作为外部类的成员
 * 
 * 以卖票为例，有100张票，几个窗口同时卖
 */
public class MultiThreadShareData {
	public static void main(String[] args) {
		ShareData data = new ShareData();
		for(int i=0;i<5;i++) {
			new Thread(data).start();
		}
	}
	
	static class  ShareData implements Runnable {
		int count = 100;
		@Override
		public void run() {
			while(true) {
				synchronized (MultiThreadShareData.class) {
					if(count <= 0) {
						System.out.println(Thread.currentThread().getName() + " 没票了！");break;
					}
					count--;
					System.out.println(Thread.currentThread().getName() + "卖出一张，还剩 : " + count);
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
}
