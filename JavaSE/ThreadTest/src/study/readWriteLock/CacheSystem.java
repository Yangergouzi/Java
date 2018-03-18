package study.readWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 读写锁技术的妙用：应用于缓存系统，比用synchronize性能更好
 * 例子：设计一个简单的缓存系统
 */
public class CacheSystem {
	public static void main(String[] args) {
		
	}
	public class CacheData{
		public Map<String, Object> cache = new HashMap<String, Object>();//缓存数据
		private ReadWriteLock rwl = new ReentrantReadWriteLock();
		public Object getData(String key) {
			rwl.readLock().lock();
			try {
				Object value = cache.get(key);//先在缓存中取数据
				if(value == null) {//如果缓存中没有数据，再在数据库中取，并写入到缓存中
					rwl.readLock().unlock();
					rwl.writeLock().lock();
					try {
						if(value == null) {//几个线程同时运行到25行，只有一个线程通过，其他线程阻塞。当写锁解锁时其他线程继续运行，会重新写value，为防止此情况，需要再判断一次
							value = "aaa";//实际中是从数据库得到
							cache.put(key, value);
						}
					}finally {
						rwl.writeLock().unlock();
					}
					rwl.readLock().lock();
				}
				return value;
			}finally {
				rwl.readLock().unlock();
			}
		}
	}
}
