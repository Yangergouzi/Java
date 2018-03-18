/*
 * 第一题：现有的程序代码模拟产生了16个日志对象，并且需要运行16秒才能打印完这些日志，请在程序中增加4个线程去调用parseLog()方法来分头打印这16个日志对象，程序只需要
	运行4秒即可打印完这些日志对象
 */
package interviewQuestion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
	
	public static void main(String[] args){
		ExecutorService threadPool = Executors.newFixedThreadPool(4);   		//增加部分
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		/*模拟处理16行日志，下面的代码产生了16个日志对象，当前代码需要运行16秒才能打印完这些日志。
		修改程序代码，开四个线程让这16个对象在4秒钟打完。
		*/
		for(int i=0;i<16;i++){  //这行代码不能改动
			final String log = ""+(i+1);//这行代码不能改动
			{
				threadPool.execute(		//增加部分
					new Runnable() {	//增加部分			
						@Override
						public void run() {		//增加部分
							Test1.parseLog(log);
						}
					}
				);					
			}
		}
		threadPool.shutdown(); //增加部分
	}
	
	//parseLog方法内部的代码不能改动
	public static void parseLog(String log){
		System.out.println(log+":"+(System.currentTimeMillis()/1000));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
}
