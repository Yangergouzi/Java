/*
 * 
      第二题：现成程序中的Test类中的代码在不断地产生数据，然后交给TestDo.doSome()方法去处理，就好像生产者在不断地产生数据，消费者在不断消费数据。请将程序改造成有10个
线程来消费生成者产生的数据，这些消费者都调用TestDo.doSome()方法去进行处理，故每个消费者都需要一秒才能处理完，程序应保证这些消费者线程依次有序地消费数据，只有上一个消
费者消费完后，下一个消费者才能消费数据，下一个消费者是谁都可以，但要保证这些消费者线程拿到的数据是有顺序的。
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
		for(int i=0;i<10;i++){  //这行不能改动
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
			
				input = i+"";  //这行不能改动
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

//不能改动此TestDo类
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