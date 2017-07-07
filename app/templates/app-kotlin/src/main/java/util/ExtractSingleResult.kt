package <%= appPackage %>.util

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import retrofit2.adapter.rxjava2.Result

class ExtractSingleResult<T>: SingleTransformer<Result<T>, T> {

    override fun apply(upstream: Single<Result<T>>?): SingleSource<T>? {
        return upstream?.flatMap {
            if (!it.isError && it.response()?.isSuccessful ?: false) {
                return@flatMap Single.just(it.response()?.body())
            }

            ExtractErrorUtil<T, T>().extractSingleError(it)
        }
    }
}
