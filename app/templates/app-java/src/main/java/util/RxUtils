package <%=appPackage%>.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
<% if (timber == true) { %>import timber.log.Timber;<% } %>

public class RxUtils {

    public static Observable<?> getRetryObservable(Observable<? extends Throwable> observable, int count) {
        return observable.zipWith(Observable.range(1, count), (throwable, integer) -> {
            if (integer < count) {
                <% if (timber == true) { %>Timber.e(throwable, "error, trying again...");<% } %>
                return Observable.timer(1, TimeUnit.SECONDS);
            }
            <% if (timber == true) { %>Timber.e(throwable, "error, finish tries...");<% } %>
            return Observable.error(new IllegalStateException());
        });
    }

    public static <T> Observable.Transformer<T, Long> zipWithFlatMap(int attempts, int backoff) {
        return observable ->
                observable
                        .zipWith(Observable.range(1, attempts), (t, repeatAttempt) -> repeatAttempt)
                        .flatMap(repeatAttempt -> Observable.timer(repeatAttempt * backoff, TimeUnit.SECONDS));
    }
}
