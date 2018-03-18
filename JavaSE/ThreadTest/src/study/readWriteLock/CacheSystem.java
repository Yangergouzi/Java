package study.readWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * ��д�����������ã�Ӧ���ڻ���ϵͳ������synchronize���ܸ���
 * ���ӣ����һ���򵥵Ļ���ϵͳ
 */
public class CacheSystem {
	public static void main(String[] args) {
		
	}
	public class CacheData{
		public Map<String, Object> cache = new HashMap<String, Object>();//��������
		private ReadWriteLock rwl = new ReentrantReadWriteLock();
		public Object getData(String key) {
			rwl.readLock().lock();
			try {
				Object value = cache.get(key);//���ڻ�����ȡ����
				if(value == null) {//���������û�����ݣ��������ݿ���ȡ����д�뵽������
					rwl.readLock().unlock();
					rwl.writeLock().lock();
					try {
						if(value == null) {//�����߳�ͬʱ���е�25�У�ֻ��һ���߳�ͨ���������߳���������д������ʱ�����̼߳������У�������дvalue��Ϊ��ֹ���������Ҫ���ж�һ��
							value = "aaa";//ʵ�����Ǵ����ݿ�õ�
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
