package org.john.test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程下 num++ 的线程安全问题（ThreadPoolExecutor 版本）
 */
class Count {
    private int num = 0;

    public void add() {
        num++;
    }

    public int getNum() {
        return num;
    }

    // ========== synchronized 修复 ==========
    private int syncedNum = 0;

    public synchronized void syncedAdd() {
        syncedNum++;
    }

    public int getSyncedNum() {
        return syncedNum;
    }

    // ========== AtomicInteger 修复 ==========
    private final AtomicInteger atomicNum = new AtomicInteger(0);

    public void atomicAdd() {
        atomicNum.incrementAndGet();
    }

    public int getAtomicNum() {
        return atomicNum.get();
    }

    // ========== 线程池版本：复现 + 修复 ==========
    public static void main(String[] args) throws InterruptedException {
        int taskCount = 50;      // 提交 50 个任务
        int loopCount = 1000;    // 每个任务累加 1000 次
        int expected = taskCount * loopCount;

        // ---------- 1. 线程池 + 不安全的 num++ ----------
        Count unsafe = new Count();
        CountDownLatch latch1 = new CountDownLatch(taskCount);

        // 创建线程池
        // 参数：核心线程数，最大线程数，空闲存活时间，时间单位，阻塞队列，线程工厂，拒绝策略
        ThreadPoolExecutor pool1 = new ThreadPoolExecutor(
                4,                          // corePoolSize：核心线程数（一直活着）
                8,                          // maximumPoolSize：最大线程数
                30L, TimeUnit.SECONDS,      // keepAliveTime：非核心线程空闲30秒后回收
                new LinkedBlockingQueue<>(200), // workQueue：有界阻塞队列，最多排队200个
                Executors.defaultThreadFactory(), // threadFactory：线程工厂
                new ThreadPoolExecutor.AbortPolicy() // handler：拒绝策略（队列满+线程满时抛异常）
        );

        System.out.println("========== 线程池版：线程安全问题复现 ==========");
        for (int i = 0; i < taskCount; i++) {
            pool1.submit(() -> {
                for (int j = 0; j < loopCount; j++) {
                    unsafe.add();  // 不安全的累加
                }
                latch1.countDown();
            });
        }

        latch1.await();                      // 等待所有任务完成
        pool1.shutdown();                    // 关闭线程池（不再接受新任务，跑完现有的）
        System.out.println("期望结果: " + expected);
        System.out.println("实际结果: " + unsafe.getNum() + "  ←  " + (unsafe.getNum() == expected ? "✔ 正确" : "✘ 丢失更新！"));
        System.out.println("线程池活跃线程数: " + pool1.getPoolSize());

        // ---------- 2. 线程池 + synchronized 修复 ----------
        Count safe = new Count();
        CountDownLatch latch2 = new CountDownLatch(taskCount);

        ThreadPoolExecutor pool2 = new ThreadPoolExecutor(
                4, 8, 30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        System.out.println("\n========== 线程池版：synchronized 修复 ==========");
        for (int i = 0; i < taskCount; i++) {
            pool2.submit(() -> {
                for (int j = 0; j < loopCount; j++) {
                    safe.syncedAdd();  // synchronized 保护
                }
                latch2.countDown();
            });
        }

        latch2.await();
        pool2.shutdown();
        System.out.println("实际结果: " + safe.getSyncedNum() + "  ←  " + (safe.getSyncedNum() == expected ? "✔ 正确" : "✘ 错误"));

        // ---------- 3. 线程池 + AtomicInteger 修复 ----------
        Count atomic = new Count();
        CountDownLatch latch3 = new CountDownLatch(taskCount);

        // 也可以使用 Executors 工具类快速创建（但阿里规范不推荐，后面讲）
        ThreadPoolExecutor pool3 = new ThreadPoolExecutor(
                4, 8, 30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        System.out.println("\n========== 线程池版：AtomicInteger 修复 ==========");
        for (int i = 0; i < taskCount; i++) {
            pool3.submit(() -> {
                for (int j = 0; j < loopCount; j++) {
                    atomic.atomicAdd();  // CAS 原子操作
                }
                latch3.countDown();
            });
        }

        latch3.await();
        pool3.shutdown();
        System.out.println("实际结果: " + atomic.getAtomicNum() + "  ←  " + (atomic.getAtomicNum() == expected ? "✔ 正确" : "✘ 错误"));

        // ---------- 额外：线程池状态展示 ----------
        System.out.println("\n========== 线程池参数说明 ==========");
        System.out.println("corePoolSize   = 4   → 核心线程数，即使空闲也不会回收");
        System.out.println("maximumPoolSize= 8   → 最大线程数，忙时最多扩容到8个");
        System.out.println("keepAliveTime  = 30s → 非核心线程空闲30秒后销毁");
        System.out.println("workQueue      = LinkedBlockingQueue(200) → 最多排队200个任务");
        System.out.println("拒绝策略       = AbortPolicy → 满了直接抛 RejectedExecutionException");
    }
}