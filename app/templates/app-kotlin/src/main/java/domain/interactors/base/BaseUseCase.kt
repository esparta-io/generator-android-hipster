package <%= appPackage %>.domain.interactors.base

import rx.Observable
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.Scheduler;
import java.util.concurrent.Executor

open class BaseUseCase(val executor: Executor) {

    fun <T> observable(action: () -> Observable<T>): Observable<T> {
        return action()
    }

    fun <T> single(action: () -> Single<T>): Single<T> {
        return action()
    }

    fun <T> callable(action: () -> T): Observable<T> {
        return Observable.fromCallable { action() }
    }

    fun <T> callableSingle(action: () -> Observable<T>): Single<Observable<T>> {
        return Single.fromCallable { action() }
    }

    fun <T> deferSingle(action: () -> Single<T>): Single<T> {
        return Single.defer { action() }
    }

    fun <T> defer(action: () -> Observable<T>): Observable<T> {
        return Observable.defer ({ action() })
    }

    fun <T> transform(e: Executor = executor): Observable.Transformer<T, T> {
        return Observable.Transformer {
            it.subscribeOn(Schedulers.from(e)).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> transformSingle(e: Executor = executor): Single.Transformer<T, T> {
        return Single.Transformer {
          it.subscribeOn(Schedulers.from(e)).observeOn(AndroidSchedulers.mainThread())
        }
    }

}
