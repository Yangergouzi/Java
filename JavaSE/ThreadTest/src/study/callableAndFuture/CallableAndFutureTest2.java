/*
 * CompletionService用于提交一组Callable，其take返回最先完成的future
 */
package study.callableAndFuture;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableAndFutureTest2 {
	public static void main(String[] args) {
		ExecutorService threadPoll = Executors.newFixedThreadPool(10);
		CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPoll);
		for(int i=1;i<=10;i++) {
			final int bird = i;
			completionService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					System.out.println("bird" + bird +"正在孵化中。。。");
					Thread.sleep(new Random().nextInt(10000));
					return "bird" + bird + " : hello！";
				}	
			});
		}
		for(int i=1;i<=10;i++) {
			try {
				System.out.println(Thread.currentThread().getName() + " 破壳! " + completionService.take().get(12, TimeUnit.SECONDS));
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}	
		}
	}
}
