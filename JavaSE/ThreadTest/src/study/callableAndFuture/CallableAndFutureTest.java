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
		System.out.println("等待结果");
		try {
			System.out.println("拿到结果：" + future.get(10, TimeUnit.SECONDS));//get里参数表示10秒内必须拿到值，不然会抛异常
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
	}
}
