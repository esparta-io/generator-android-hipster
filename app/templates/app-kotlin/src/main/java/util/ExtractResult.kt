package <%= appPackage %>.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.adapter.rxjava.Result

/**
 * Created by gmribas on 22/02/17.
 */
class ExtractResult<T> : ObservableTransformer<Result<T>, T> {

    override fun apply(upstream: Observable<Result<T>>?): ObservableSource<T>? {
        return upstream?.flatMap {
            if (!it.isError && it.response().isSuccessful) {
                return@flatMap Observable.just(it.response().body())
            }

            ExtractErrorUtil<T, T>().extractError(it)
        }
    }
}