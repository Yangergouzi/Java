/*
 * 
      �ڶ��⣺�ֳɳ����е�Test���еĴ����ڲ��ϵز������ݣ�Ȼ�󽻸�TestDo.doSome()����ȥ�����ͺ����������ڲ��ϵز������ݣ��������ڲ����������ݡ��뽫����������10��
�߳������������߲��������ݣ���Щ�����߶�����TestDo.doSome()����ȥ���д�����ÿ�������߶���Ҫһ����ܴ����꣬����Ӧ��֤��Щ�������߳�����������������ݣ�ֻ����һ����
�������������һ�������߲����������ݣ���һ����������˭�����ԣ���Ҫ��֤��Щ�������߳��õ�����������˳��ġ�
 */
package interviewQuestion;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test2 {
	static String input;
	static Lock lock = new ReentrantLock();
	static Condition condition = lock.newCondition();
	static boolean shouldMain = true;
	public static void main(String[] args) {
		ExecutorService threadpool = Executors.newFixedThreadPool(10);
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		for(int i=0;i<10;i++){  //���в��ܸĶ�
			lock.lock();
			try {
				while(!shouldMain) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				input = i+"";  //���в��ܸĶ�
			//	System.out.println(Thread.currentThread().getName() + " generate " + input);
				shouldMain = false;
				condition.signal();
			}finally {
				lock.unlock();
			}
			
			threadpool.execute(new Runnable() {		
				@Override
				public void run() {
					lock.lock();
					try {
						while(shouldMain) {
							try {
								condition.await();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						String output = TestDo1.doSome(input);
						System.out.println(Thread.currentThread().getName()+ ":" + output);
						shouldMain = true;
						condition.signal();
					}finally {
						lock.unlock();
					}
				}
			});
		}
		threadpool.shutdown();
		
		
	}
}

//���ܸĶ���TestDo��
class TestDo1 {
	public static String doSome(String input){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String output = input + ":"+ (System.currentTimeMillis() / 1000);
		return output;
	}
}