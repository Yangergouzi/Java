package study.callableAndFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableAndFutureTest {
	public static void main(String[] args) {
		ExecutorService threadPoll = Executors.newSingleThreadExecutor();
		
		Future<String> future = threadPoll.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("this is a callable!");
				Thread.sleep(2000);
				return "hello";
			}	
		});
		System.out.println("�ȴ����");
		try {
			System.out.println("�õ������" + future.get(10, TimeUnit.SECONDS));//get�������ʾ10���ڱ����õ�ֵ����Ȼ�����쳣
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
	}
}
