package <%= appPackage %>.domain.interactors.base

import rx.Observable
import rx.Scheduler
import rx.Single
import rx.android.schedulers.AndroidSchedulers

open class BaseUseCase(val executor: Scheduler) {

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
        return Observable.defer({ action() })
    }

    fun <T> transform(e: Scheduler = executor): Observable.Transformer<T, T> {
        return Observable.Transformer {
            it.subscribeOn(e).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> transformSingle(e: Scheduler = executor): Single.Transformer<T, T> {
        return Single.Transformer {
            it.subscribeOn(e).observeOn(AndroidSchedulers.mainThread())
        }
    }

}
