package study.shareData;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
 * ThreadLocal实现线程范围的共享变量
 */
public class ThreadLocalTest {
	private static ThreadLocal<Integer> threadData1 = new ThreadLocal<Integer>();
	private static ThreadLocal<Person> threadData2 = new ThreadLocal<Person>();
	public static void main(String[] args) {
		for(int i = 0;i < 2;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt(999);
					Person person = new Person();
					person.setAge(data);
					person.setName("name" + data);
					threadData1.set(data);
					threadData2.set(person);
					Person person2 = Person.getInstance();
					person2.setName("name" + data);
					person2.setAge(data);
					System.out.println(Thread.currentThread().getName() + " set data1: " + threadData1.get());
					System.out.println(Thread.currentThread().getName() + " set data2: " + threadData2.get().getName() + " - " + threadData2.get().getAge());
					System.out.println(Thread.currentThread().getName() + " set data2(优): " + person2.getName() + " - " + person2.getAge());
					new A().getThreadData1();
					new A().getThreadData2();
					new A().getThreadData2better();
				}
			}).start();
		}
	}
	
	static class A{
		public void getThreadData1() {
			System.out.println(Thread.currentThread().getName() + "has get data1 :" + threadData1.get());
		}
		
		public void getThreadData2() {
			System.out.println(Thread.currentThread().getName() + "has get data2 " + threadData2.get().getName() + " - " + threadData2.get().getAge() );
		}
		
		public void getThreadData2better() {
			System.out.println(Thread.currentThread().getName() + "has get data2(优) " + Person.getInstance().getName() + " - " + Person.getInstance().getAge() );
		}
	}
	
	static class Person {	
		/*
		 * 创建单例模式的方法
		private Person() {}
		private static Person instance = null;
		public static synchronized Person getInstance() {
			if(instance == null) {
				instance = new Person();
			}
			return instance;
		}*/
		/*
		 * 优雅地使用ThreadLocal的方法
		 * 表示每个线程会创建单例的Person，彼此的Person数据互不干扰
		 */
		private static ThreadLocal<Person> threadLocalData = new ThreadLocal<Person>();
		private Person() {}
		public static /*synchronized*/ Person getInstance() {
			Person instance = threadLocalData.get();
			if(instance == null) {
				instance = new Person();
				threadLocalData.set(instance);
			}
			return instance;
		}
		
		
		private String name;
		private int age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		
	}
	
}
