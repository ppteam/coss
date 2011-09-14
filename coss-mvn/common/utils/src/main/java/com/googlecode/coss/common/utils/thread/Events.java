package com.googlecode.coss.common.utils.thread;

import static java.util.concurrent.Executors.newScheduledThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.googlecode.coss.common.utils.thread.threadPool.DebugableThreadFactory;

public final class Events {

    public static int executorNum  = Runtime.getRuntime().availableProcessors();
    public static int schedulerNum = 1;

    static {
        EXECUTOR = newFixThreadPool(executorNum,
                new DebugableThreadFactory("events-executor", true));
        SCHEDULER = newScheduledThreadPool(schedulerNum, new DebugableThreadFactory(
                "events-scheduler", true));
        final Thread shutdownEvents = new Thread(new Runnable() {
            @Override
            public void run() {
                dispose();
            }
        }, "shutdown-events");
        Runtime.getRuntime().addShutdownHook(shutdownEvents);
    }

    public static void dispose() {
        try {
            do {
                SCHEDULER.shutdownNow();
            } while (!SCHEDULER.awaitTermination(500L, TimeUnit.MILLISECONDS));
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        try {
            do {
                EXECUTOR.shutdownNow();
            } while (!EXECUTOR.awaitTermination(500L, TimeUnit.MILLISECONDS));
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** @see java.util.concurrent.ExecutorService#execute(Runnable) */
    public static void enqueue(final Runnable task) {
        EXECUTOR.execute(task);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, final long period,
                                                         final TimeUnit unit) {
        return scheduleAtFixedRate(event(command), period, period, unit);
    }

    /**
     * @see java.util.concurrent.ScheduledExecutorService#scheduleAtFixedRate(Runnable,
     *      long, long, TimeUnit)
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay,
                                                         long period, TimeUnit unit) {
        return SCHEDULER.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    private static Runnable event(final Runnable command) {
        return new Runnable() {

            @Override
            public void run() {
                EXECUTOR.execute(command);
            }
        };
    }

    private static ThreadPoolExecutor newFixThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(nThreads * 2), threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private final static ExecutorService          EXECUTOR;
    private final static ScheduledExecutorService SCHEDULER;
}
