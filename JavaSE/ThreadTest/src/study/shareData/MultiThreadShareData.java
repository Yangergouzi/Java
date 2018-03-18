package study.shareData;
/*���ÿ���߳�ִ��ͬһ�δ��룬������ͬһ��Runable������������Runable���棨��Ʊϵͳ��
 *���ÿ���߳�ִ�д��벻ͬ������̼߳乲�����������ַ�ʽ��
 * 1.���������ݷ�װ��һ�������У�Ȼ����һ���ݸ�����Runable����
 * 2.������Runable������Ϊ�ڲ��࣬����������Ϊ�ⲿ��ĳ�Ա
 * 
 * ����ƱΪ������100��Ʊ����������ͬʱ��
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
						System.out.println(Thread.currentThread().getName() + " ûƱ�ˣ�");break;
					}
					count--;
					System.out.println(Thread.currentThread().getName() + "����һ�ţ���ʣ : " + count);
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
