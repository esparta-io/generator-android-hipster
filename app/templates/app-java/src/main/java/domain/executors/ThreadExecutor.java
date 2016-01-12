package <%= appPackage %>.domain.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface ThreadExecutor extends Executor {

    Future<?> submit(Runnable task);

    <T> Future<T> submit(Callable<T> task);

}
