package org.ado.java.tutorial.thread;

/**
 * 问题：有A,B,C三个线程, A线程输出A, B线程输出B, C线程输出C，要求, 同时启动三个线程, 按顺序输出ABC, 循环10次。
 * 
 * @author ado1986
 *
 */
public class PrintABC {
	private volatile static int flag = 1;

	private static int count = 0;

	private static Object o = new Object();

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (o) {
					while (count < 10) {
						while (flag != 1) {
							try {
								o.wait();
							} catch (InterruptedException e) {
							}
						}
						System.out.print("A");
						count++;
						flag = 2;
						o.notifyAll();
					}
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (o) {
					while (count < 10) {
						while (flag != 2) {
							try {
								o.wait();
							} catch (InterruptedException e) {
							}
						}
						System.out.print("B");
						flag = 3;
						o.notifyAll();
					}
				}
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (o) {
					while (count < 10) {
						while (flag != 3) {
							try {
								o.wait();
							} catch (InterruptedException e) {
							}
						}
						System.out.print("C");
						flag = 1;
						o.notifyAll();
					}
				}
			}
		});

		t1.start();
		t2.start();
		t3.start();
	}

}
