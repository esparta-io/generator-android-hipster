package <%= appPackage %>.domain.executors

import android.util.Log

import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class JobExecutor : ThreadExecutor {

  private var corePoolSize = INITIAL_POOL_SIZE

  private val lock = Object()

  private var waitBeforeShutdown = false

  private var maxPoolSize = Integer.MAX_VALUE

  private var queueCapacity = Integer.MAX_VALUE

  private var awaitTerminationInSeconds = 0

  private var executor: ExecutorService

  private val workQueue: BlockingQueue<Runnable>

  val threadPoolExecutor: ThreadPoolExecutor?

  private var threadFactory: ThreadFactory? = null

  private var keepAliveSeconds = 60

  init {
    this.workQueue = createQueue(queueCapacity)
    this.threadFactory = JobThreadFactory()
    this.threadPoolExecutor = ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds.toLong(), TimeUnit.SECONDS, this.workQueue, this.threadFactory)
    this.executor = threadPoolExecutor
  }

  fun setQueueCapacity(queueCapacity: Int) {
    this.queueCapacity = queueCapacity
  }

  protected fun createQueue(queueCapacity: Int): BlockingQueue<Runnable> {
    if (queueCapacity > 0) {
      return LinkedBlockingQueue(queueCapacity)
    } else {
      return SynchronousQueue()
    }
  }

  override fun execute(task: Runnable) {
    val executor = threadPoolExecutor
    executor!!.execute(task)
  }

  override fun submit(task: Runnable): Future<*> {
    val executor = threadPoolExecutor
    return executor!!.submit(task)
  }

  override fun <T> submit(task: Callable<T>): Future<T> {
    val executor = threadPoolExecutor
    return executor!!.submit(task)
  }

  private class JobThreadFactory : ThreadFactory {

    private val counter = 0

    override fun newThread(runnable: Runnable): Thread {
      return Thread(runnable, THREAD_NAME + counter)
    }

    companion object {

      private val THREAD_NAME = "app_"
    }
  }

  fun destroy() {
    shutdown()
  }

  fun shutdown() {
    if (waitBeforeShutdown) {
      executor.shutdown()
    } else {
      executor.shutdownNow()
    }
    awaitTermination()
  }

  private fun awaitTermination() {
    if (awaitTerminationInSeconds > 0) {
      try {
        if (!executor.awaitTermination(awaitTerminationInSeconds.toLong(), TimeUnit.SECONDS)) {
          Log.w("TRT9JobExecutor", "Timed out while waiting for executor to terminate")
        }
      } catch (ex: InterruptedException) {
        Log.w("TRT9JobExecutor", "Interrupted while waiting for executor to terminate")
      }

      Thread.currentThread().interrupt()
    }
  }

  fun setCorePoolSize(corePoolSize: Int) {
    synchronized (lock) {
      this.corePoolSize = corePoolSize
      if (this.threadPoolExecutor != null) {
        this.threadPoolExecutor.corePoolSize = corePoolSize
      }
    }
  }

  fun setMaxPoolSize(maxPoolSize: Int) {
    synchronized (lock) {
      this.maxPoolSize = maxPoolSize
      if (this.threadPoolExecutor != null) {
        this.threadPoolExecutor.maximumPoolSize = maxPoolSize
      }
    }
  }

  fun setKeepAliveSeconds(keepAliveSeconds: Int) {
    synchronized (lock) {
      this.keepAliveSeconds = keepAliveSeconds
      this.threadPoolExecutor?.setKeepAliveTime(keepAliveSeconds.toLong(), TimeUnit.SECONDS)
    }
  }

  fun setThreadFactory(threadFactory: ThreadFactory) {
    this.threadFactory = threadFactory
  }

  fun setWaitBeforeShutdown(waitForJobsToCompleteOnShutdown: Boolean) {
    this.waitBeforeShutdown = waitForJobsToCompleteOnShutdown
  }

  fun setAwaitTerminationInSeconds(awaitTerminationInSeconds: Int) {
    this.awaitTerminationInSeconds = awaitTerminationInSeconds
  }

  companion object {
    private val INITIAL_POOL_SIZE = 4
  }


}
