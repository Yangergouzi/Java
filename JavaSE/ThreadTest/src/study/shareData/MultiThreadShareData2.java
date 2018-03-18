package study.shareData;
/*���ÿ���߳�ִ��ͬһ�δ��룬������ͬһ��Runable������������Runable���棨��Ʊϵͳ��
 *���ÿ���߳�ִ�д��벻ͬ������̼߳乲�����������ַ�ʽ��
 * 1.���������ݷ�װ��һ�������У�Ȼ����һ���ݸ�����Runable����
 * 2.������Runable������Ϊ�ڲ��࣬����������Ϊ�ⲿ��ĳ�Ա
 * 
 * ���4���̣߳����������߳�ÿ�ζ�i��һ����������ÿ�ζ�i��һ
 */
public class MultiThreadShareData2 {
//	private static ShareData data2 = new ShareData();
	public static void main(String[] args) {	
		//����1
		ShareData data1 = new ShareData();
		for(int t=0;t<2;t++) {
			MyRunable1 myRunable1 = new MyRunable1(data1);
			MyRunable2 myRunable2 = new MyRunable2(data1);
			new Thread(myRunable1).start();
			new Thread(myRunable2).start();
		}
		
		//����2
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
