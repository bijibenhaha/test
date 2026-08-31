package org.john;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 业务场景：需要处理 10 万条任务，每条任务模拟 IO 耗时 100ms。
 * <p>
 * 线程池参数设置依据：
 * 1）IO 密集型任务：CPU 用于等待 IO，并不会满载，因此可适当放大线程数。
 *    推荐公式：corePoolSize = 200，maximumPoolSize = 400。
 * 2）队列：使用有界队列 ArrayBlockingQueue(1000)，防止 10 万任务一次性入队
 *    导致内存溢出（OOM）。
 * 3）拒绝策略：CallerRunsPolicy —— 当队列满且线程池已达上限时，
 *    由提交任务的主线程执行该任务，自然减缓提交速度，避免任务丢失。
 */
public class IoTaskDemo {

    private static final int TASK_COUNT = 100000;     // 10 万条任务
    private static final int IO_MS = 100;               // 每条任务 IO 耗时 100ms
    private static final int QUEUE_CAPACITY = 1000;    // 有界队列容量

    public static void main(String[] args) throws InterruptedException {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        int corePoolSize = 200;        // 核心线程数
        int maximumPoolSize = 400;     // 最大线程数（IO 密集型放大 4 倍）

        System.out.println("CPU cores: " + cpuCores
                + " -> corePoolSize=" + corePoolSize
                + ", maximumPoolSize=" + maximumPoolSize
                + ", queueCapacity=" + QUEUE_CAPACITY);

        AtomicLong done = new AtomicLong();
        AtomicInteger threadSeq = new AtomicInteger();

        ThreadFactory factory = r -> {
            Thread t = new Thread(r, "IoWorker-" + threadSeq.getAndIncrement());
            t.setDaemon(false);
            return t;
        };

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                factory,
                new ThreadPoolExecutor.CallerRunsPolicy());

        long start = System.currentTimeMillis();
        for (int i = 0; i < TASK_COUNT; i++) {
            pool.submit(() -> {
                try {
                    Thread.sleep(IO_MS);            // 模拟 IO 耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // 每做完1w个任务，打个快照
                long c = done.incrementAndGet();
                if (c % 10_000 == 0) {
                    System.out.println("Completed " + c + "/" + TASK_COUNT
                            + "  pool=[core=" + pool.getCorePoolSize()
                            + ", max=" + pool.getMaximumPoolSize()
                            + ", active=" + pool.getActiveCount()
                            + ", queued=" + pool.getQueue().size()
                            + ", completed=" + pool.getCompletedTaskCount() + "]");
                }
            });
        }

        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(10);
        }

        long cost = System.currentTimeMillis() - start;
        System.out.println("All tasks done. total=" + done.get()
                + ", elapsed=" + cost + "ms (" + (cost / 1000) + "s)");
    }
}
