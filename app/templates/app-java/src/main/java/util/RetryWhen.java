package <%= appPackage %>.util;

import android.support.v4.util.Pair;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by deividi on 13/04/16.
 */
public class RetryWhen<T> implements rx.Observable.Transformer<T, T> {

    private final int count;
    private final int period;
    private final int backoff;

    public RetryWhen() {
        count = 3;
        period = 1000;
        backoff = 0;
    }

    public RetryWhen(int count) {
        this.count = count;
        period = 1000;
        backoff = 0;
    }

    public RetryWhen(int count, int period) {
        this.count = count;
        this.period = period;
        backoff = 0;
    }

    public RetryWhen(int count, int period, int backoff) {
        this.count = count;
        this.period = period;
        this.backoff = backoff;
    }

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.retryWhen(observable -> observable
                .zipWith(Observable.range(1, count), Pair::create)
                .flatMap(o -> {
                    if (o.second < count) {
                        return Observable.timer(period + (period * backoff * count), TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(o.first);
                }));
    }
}
