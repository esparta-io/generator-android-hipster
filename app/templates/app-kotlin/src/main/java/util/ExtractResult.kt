package <%= appPackage %>.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.adapter.rxjava2.Result

class ExtractResult<T> : ObservableTransformer<Result<T>, T> {

   override fun apply(upstream: Observable<Result<T>>?): ObservableSource<T>? {
       return upstream?.flatMap {
           if (!it.isError && it.response()?.isSuccessful ?: false) {
               return@flatMap Observable.just(it.response()!!.body())
           }

           ExtractErrorUtil<T, T>().extractError(it)
       }
   }
}
