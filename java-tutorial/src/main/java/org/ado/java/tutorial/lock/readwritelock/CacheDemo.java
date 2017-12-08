package org.ado.java.tutorial.lock.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 
 * 线程进入读锁的前提条件： 
 * 1.没有其他线程的写锁， 
 * 2.没有写请求或者有写请求，但调用线程和持有锁的线程是同一个
 * 
 * 线程进入写锁的前提条件： 
 * 1.没有其他线程的读锁 没有其他线程的写锁
 * 
 * @author ado1986
 *
 */
public class CacheDemo {
	private Map<String, Object> map = new HashMap<String, Object>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		final CacheDemo cache = new CacheDemo();
		Executor exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++)
			exec.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println(cache.get("test"));
				}
			});
	}

	public Object get(String id) {
		Object value = null;
		try {
			rwl.readLock().lock();
			value = map.get(id);
			if (value == null) {
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					value = "" + Math.random();
					map.put(id, value);
				} finally {
					rwl.writeLock().unlock();
				}
				rwl.readLock().lock();
			}
		} finally {
			rwl.readLock().unlock();
		}
		return value;
	}
}
