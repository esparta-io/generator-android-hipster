package <%= appPackage %>.domain.interactors.base

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers

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

    fun <T> transform(e: Scheduler = executor): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(e).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> transformSingle(e: Scheduler = executor): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(e).observeOn(AndroidSchedulers.mainThread())
        }
    }

}
