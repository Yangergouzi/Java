/*
 *  �����⣺���г���ͬʱ������4���߳�ȥ����TestDo.doSome(key, value)����������TestDo.doSome(key, value)�����ڵĴ���������ͣ1�룬Ȼ�����������Ϊ��λ�ĵ�ǰʱ��
 *  ֵ�����ԣ����ӡ��4����ͬ��ʱ��ֵ��������ʾ��
		4:4:1258199615
		1:1:1258199615
		3:3:1258199615
		1:2:1258199615
        ���޸Ĵ��룬����м����̵߳���TestDo.doSome(key, value)����ʱ�����ݽ�ȥ��key��ȣ�equals�Ƚ�Ϊtrue�������⼸���߳�Ӧ�����Ŷ��������������������̵߳�key����
        "1"ʱ�������е�һ��Ҫ�����������߳���1����������������ʾ��
		4:4:1258199615
		1:1:1258199615
		3:3:1258199615
		1:2:1258199616
	  ��֮����ÿ���߳���ָ����key���ʱ����Щ���key���߳�Ӧÿ��һ���������ʱ��ֵ��Ҫ�û��⣩�����key��ͬ������ִ�У��໥֮�䲻���⣩
 */

package interviewQuestion;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//���ܸĶ���Test��	
	public class Test3 extends Thread{
		
		private TestDo testDo;
		private String key;
		private String value;
		
		public Test3(String key,String key2,String value){
			this.testDo = TestDo.getInstance();
			/*����"1"��"1"��ͬһ�������������д������Ҫ��"1"+""�ķ�ʽ�����µĶ���
			��ʵ������û�иı䣬��Ȼ��ȣ�����Ϊ"1"����������ȴ������ͬһ����Ч��*/
			this.key = key+key2; 
			this.value = value;
		}


		public static void main(String[] args) throws InterruptedException{
			Test3 a = new Test3("1","","1");
			Test3 b = new Test3("1","","2");
			Test3 c = new Test3("3","","3");
			Test3 d = new Test3("4","","4");
			System.out.println("begin:"+(System.currentTimeMillis()/1000));
			a.start();
			b.start();
			c.start();
			d.start();

		}
		
		public void run(){
			testDo.doSome(key, value);
		}
	}

	class TestDo {

		private TestDo() {}
		private static TestDo _instance = new TestDo();	
		public static TestDo getInstance() {
			return _instance;
		}
		
		private CopyOnWriteArrayList<Object> keyList = new CopyOnWriteArrayList<Object>();
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		
		public void doSome(Object key, String value) {
			boolean isRepeat = false;
			lock.lock();
			for(Object k : keyList){
				if(key.equals(k)) {
					isRepeat = true;
				}
			}
			keyList.add(key);
			if(isRepeat){
				try {
					condition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lock.unlock();
			// �Դ������ڵ�����Ҫ�ֲ�ͬ���Ĵ��룬���ܸĶ�!
			{
				try {
					Thread.sleep(1000);
					System.out.println(key+":"+value + ":"
							+ (System.currentTimeMillis() / 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			lock.lock();
			condition.signal();
			lock.unlock();
		}
		

	}
