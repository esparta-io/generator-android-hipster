package <%= appPackage %>.domain.executors;

import android.util.Log;

import javax.inject.Inject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExecutor implements ThreadExecutor {

    private static final int INITIAL_POOL_SIZE = 4;

    private int corePoolSize = INITIAL_POOL_SIZE;

    private final Object lock = new Object();

    private boolean waitBeforeShutdown = false;

    private int maxPoolSize = Integer.MAX_VALUE;

    private int queueCapacity = Integer.MAX_VALUE;

    private int awaitTerminationInSeconds = 0;

    private ExecutorService executor;

    private final BlockingQueue<Runnable> workQueue;

    private final ThreadPoolExecutor threadPoolExecutor;

    private ThreadFactory threadFactory;

    private int keepAliveSeconds = 60;

    @Inject
    public JobExecutor() {
        this.workQueue = createQueue(queueCapacity);
        this.threadFactory = new JobThreadFactory();
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, this.workQueue, this.threadFactory);
        this.executor = threadPoolExecutor;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        if (queueCapacity > 0) {
            return new LinkedBlockingQueue<>(queueCapacity);
        } else {
            return new SynchronousQueue<>();
        }
    }

    public ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        return threadPoolExecutor;
    }

    @Override
    public void execute(Runnable task) {
        Executor executor = getThreadPoolExecutor();
        executor.execute(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        ExecutorService executor = getThreadPoolExecutor();
        return executor.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    private static class JobThreadFactory implements ThreadFactory {

        private static final String THREAD_NAME = "trt9_";

        private int counter = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, THREAD_NAME + counter);
        }
    }

    public void destroy() {
        shutdown();
    }

    public void shutdown() {
        if (waitBeforeShutdown) {
            executor.shutdown();
        } else {
            executor.shutdownNow();
        }
        awaitTermination();
    }

    private void awaitTermination() {
        if (awaitTerminationInSeconds > 0) {
            try {
                if (!executor.awaitTermination(awaitTerminationInSeconds, TimeUnit.SECONDS)) {
                    Log.w("TRT9JobExecutor", "Timed out while waiting for executor to terminate");
                }
            } catch (InterruptedException ex) {
                Log.w("TRT9JobExecutor", "Interrupted while waiting for executor to terminate");
            }
            Thread.currentThread().interrupt();
        }
    }

    public void setCorePoolSize(int corePoolSize) {
        synchronized (lock) {
            this.corePoolSize = corePoolSize;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setCorePoolSize(corePoolSize);
            }
        }
    }

    public void setMaxPoolSize(int maxPoolSize) {
        synchronized (lock) {
            this.maxPoolSize = maxPoolSize;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
            }
        }
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        synchronized (lock) {
            this.keepAliveSeconds = keepAliveSeconds;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setKeepAliveTime(keepAliveSeconds, TimeUnit.SECONDS);
            }
        }
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public void setWaitBeforeShutdown(boolean waitForJobsToCompleteOnShutdown) {
        this.waitBeforeShutdown = waitForJobsToCompleteOnShutdown;
    }

    public void setAwaitTerminationInSeconds(int awaitTerminationInSeconds) {
        this.awaitTerminationInSeconds = awaitTerminationInSeconds;
    }


}
