package org.john;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadFactory;
/**
 * 业务场景：需要处理10万条任务，每条任务模拟IO耗时100ms。
 * 使用ThreadPoolExecutor手动创建线程池，不要直接用Executors工具类。
 * 合理设置核心参数（核心线程数、最大线程、队列、拒绝策略），写出代码。
 */
public class Test1 {
    private static final int AMOUNT = 100000;
    private static final int coreThread = 200;
    private static final int noncoreThread = 400;

    private static final AtomicInteger POOL_THREAD_SEQ = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = r -> {
        Thread t = new Thread(r, "SumPool-" + POOL_THREAD_SEQ.getAndIncrement());
        t.setDaemon(false);
        t.setPriority(Thread.NORM_PRIORITY);
        return t;
    };

    public static void main(String[] args) throws InterruptedException {
//        long start = System.currentTimeMillis();
//        execute();
//        long interval = System.currentTimeMillis() - start;
//        System.out.printf("程序运行耗时: %d\n",interval);
        String str = "JEYSHALINI TEVOSHA A/P R.SETHURAMAN @ EDWARD LAWRENCE RAMADAS";
        System.out.println("the length of the string is "+str.length());
    }

    public static void execute() throws InterruptedException {
        int cpuCore = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor pool = new ThreadPoolExecutor
                (coreThread, noncoreThread,
                40L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                THREAD_FACTORY, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < AMOUNT; i++) {
            pool.submit(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(10);
        }

    }
}
