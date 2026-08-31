package org.john;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写程序：开启20个线程，共同完成1‑10000数字累加，最终得到总和。
 * 要求：分别用4种方式实现 **主要是线程池**
 * 1）synchronized 同步锁；2）AtomicInteger；3）ReentrantLock 4）线程池
 */
public class SumTest {

    private static final int THREAD_COUNT = 20;
    private static final int RANGE = 10000;
    private static final int STEP = RANGE / THREAD_COUNT; // 500 per thread

    private static final AtomicInteger POOL_THREAD_SEQ = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = r -> {
        Thread t = new Thread(r, "SumPool-" + POOL_THREAD_SEQ.getAndIncrement());
        t.setDaemon(false);
        t.setPriority(Thread.NORM_PRIORITY);
        return t;
    };
    private static final RejectedExecutionHandler REJECTION_HANDLER =
            new ThreadPoolExecutor.CallerRunsPolicy();

    public static void main(String[] args) throws InterruptedException {
//        // 计时
//        long start1 = System.currentTimeMillis();
//        int result = sumWithSynchronized();
//        long cost1 = System.currentTimeMillis() - start1;
//        System.out.printf("synchronized方法结果是：%d, 用时：%d ms\n",result,cost1);

        // 获取CPU逻辑核心数（超线程后，Java拿到的是这个）
//        int cpuCore = Runtime.getRuntime().availableProcessors();
//        System.out.println("CPU逻辑核心数：" + cpuCore);

        System.out.println("Expected sum (1-10000): " + expectedSum());
        System.out.println("synchronized result  : " + sumWithSynchronized());
        System.out.println("AtomicInteger result : " + sumWithAtomicInteger());
        System.out.println("ReentrantLock result : " + sumWithReentrantLock());
        System.out.println("ThreadPool result     : " + sumWithThreadPool());
    }

    private static int expectedSum() {
        int sum = 0;
        for (int i = 1; i <= RANGE; i++) {
            sum += i;
        }
        return sum;
    }

    private static int sumWithSynchronized() throws InterruptedException {

        final int[] sum = {0};
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int start = i * STEP + 1;
            final int end = (i + 1) * STEP;
            threads[i] = new Thread(() -> {
                int local = 0;
                for (int j = start; j <= end; j++) {
                    local += j;
                }
                // synchronized(sum)：以sum数组作为锁监视器对象。
                // 同一时刻，最多只有 1 个线程可以进入这个大括号 {} 里面的代码；其他线程执行到这里会阻塞等待。
                synchronized (sum) {
                    sum[0] += local;
                }
            });
            threads[i].start();
        }
        // t.join()：让当前正在运行的线程（这里是主线程）停下来，等线程t执行完毕，再继续。
        // 在这里的目的：保证所有子线程全部计算完成之后，再返回总和 sum，避免拿到未完成结果。
        for (Thread t : threads) {
            t.join();
        }
        return sum[0];
    }

    private static int sumWithAtomicInteger() throws InterruptedException {
        AtomicInteger sum = new AtomicInteger(0);
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int start = i * STEP + 1;
            final int end = (i + 1) * STEP;
            threads[i] = new Thread(() -> {
                int local = 0;
                for (int j = start; j <= end; j++) {
                    local += j;
                }
                sum.addAndGet(local);
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        return sum.get();
    }

    private static int sumWithReentrantLock() throws InterruptedException {
        final int[] sum = {0};
        ReentrantLock lock = new ReentrantLock();
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int start = i * STEP + 1;
            final int end = (i + 1) * STEP;
            threads[i] = new Thread(() -> {
                int local = 0;
                for (int j = start; j <= end; j++) {
                    local += j;
                }
                lock.lock();
                try {
                    sum[0] += local;
                } finally {
                    lock.unlock();
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        return sum[0];
    }

    private static int sumWithThreadPool() throws InterruptedException {
        final int[] sum = {0};
        ReentrantLock lock = new ReentrantLock();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                THREAD_COUNT, THREAD_COUNT,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                THREAD_FACTORY,
                REJECTION_HANDLER);
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int start = i * STEP + 1;
            final int end = (i + 1) * STEP;
            pool.submit(() -> {
                int local = 0;
                for (int j = start; j <= end; j++) {
                    local += j;
                }
                lock.lock();
                try {
                    sum[0] += local;
                } finally {
                    lock.unlock();
                }
            });
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(10);
        }
        return sum[0];
    }
}
