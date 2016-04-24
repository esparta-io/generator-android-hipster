package <%= appPackage %>.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import java.util.concurrent.Semaphore;

import javax.annotation.Nonnull;

/**
 * Created by deividi on 08/04/16.
 */
public class AsyncHandler extends Handler {

    public AsyncHandler(String name) {
        this(name, Process.THREAD_PRIORITY_BACKGROUND);
    }

    protected AsyncHandler(String handlerName, int handlerPriority) {
        super(startHandlerThread(handlerName, handlerPriority));
    }

    private static Looper startHandlerThread(String name, int priority) {
        final Semaphore semaphore = new Semaphore(0);
        HandlerThread handlerThread = new HandlerThread(name, priority) {
            protected void onLooperPrepared() {
                semaphore.release();
            }
        };
        handlerThread.start();
        semaphore.acquireUninterruptibly();
        return handlerThread.getLooper();
    }

    public void dispatchMessage(Message message) {
        super.dispatchMessage(message);
    }

    public void quit() {
        getLooper().quit();
    }

    public boolean sendMessageAtTime(@Nonnull Message message, long uptimeMillis) {
        return super.sendMessageAtTime(message, uptimeMillis);
    }

}
