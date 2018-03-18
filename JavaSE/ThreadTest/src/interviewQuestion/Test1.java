/*
 * ��һ�⣺���еĳ������ģ�������16����־���󣬲�����Ҫ����16����ܴ�ӡ����Щ��־�����ڳ���������4���߳�ȥ����parseLog()��������ͷ��ӡ��16����־���󣬳���ֻ��Ҫ
	����4�뼴�ɴ�ӡ����Щ��־����
 */
package interviewQuestion;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
	
	public static void main(String[] args){
		ExecutorService threadPool = Executors.newFixedThreadPool(4);   		//���Ӳ���
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		/*ģ�⴦��16����־������Ĵ��������16����־���󣬵�ǰ������Ҫ����16����ܴ�ӡ����Щ��־��
		�޸ĳ�����룬���ĸ��߳�����16��������4���Ӵ��ꡣ
		*/
		for(int i=0;i<16;i++){  //���д��벻�ܸĶ�
			final String log = ""+(i+1);//���д��벻�ܸĶ�
			{
				threadPool.execute(		//���Ӳ���
					new Runnable() {	//���Ӳ���			
						@Override
						public void run() {		//���Ӳ���
							Test1.parseLog(log);
						}
					}
				);					
			}
		}
		threadPool.shutdown(); //���Ӳ���
	}
	
	//parseLog�����ڲ��Ĵ��벻�ܸĶ�
	public static void parseLog(String log){
		System.out.println(log+":"+(System.currentTimeMillis()/1000));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
}
