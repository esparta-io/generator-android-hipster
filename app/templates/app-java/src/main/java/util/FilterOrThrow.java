package <%= appPackage %>.util;


import com.rumo.chavenamao.util.google.common.base.Optional;

import rx.Observable;

/**
 * Created by deividi on 13/04/16.
 */
public class FilterOrThrow<T> implements Observable.Transformer<Optional<T>, T> {
    @Override
    public Observable<T> call(Observable<Optional<T>> tObservable) {
        return tObservable.flatMap(o -> {
            if (o.isPresent()) {
                return Observable.just(o.get());
            }
            return Observable.error(new IllegalStateException("Value is not valid!" + o.toString()));
        });
    }
}
