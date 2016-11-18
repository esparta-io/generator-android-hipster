package <%= appPackage %>.util;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by deividi on 13/04/16.
 */
public class CallInExecutorThanMainThread<T> implements Observable.Transformer<T, T> {
    private final Scheduler executor;

    public CallInExecutorThanMainThread(Scheduler executor) {
        this.executor = executor;
    }

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(executor).observeOn(AndroidSchedulers.mainThread());
    }
}
