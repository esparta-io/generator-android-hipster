package <%= appPackage %>.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by gmribas on 03/03/17.
 */
class RetryWhen<T>: ObservableTransformer<T, T> {

    private var count: Int by Delegates.notNull<Int>()
    private var period: Int by Delegates.notNull<Int>()
    private var backoff: Int by Delegates.notNull<Int>()

    constructor() {
        count = 3
        period = 1000
        backoff = 0
    }

    constructor(count: Int) {
        this.count = count
        period = 1000
        backoff = 0
    }

    constructor(count: Int, period: Int) {
        this.count = count
        this.period = period
        backoff = 0
    }

    constructor(count: Int, period: Int, backoff: Int) {
        this.count = count
        this.period = period
        this.backoff = backoff
    }

    override fun apply(upstream: Observable<T>?): ObservableSource<T> {
        upstream?.let {
            return it.retryWhen {
                it.cast(Throwable::class.java)
                        .zipWith(1..count, { throwable, counter -> Pair<Throwable, Int>(throwable, counter) })
                        .flatMap {
                            if (it.second < count)
                                Observable.timer((period + period * backoff * count).toLong(), TimeUnit.MILLISECONDS)
                            else
                                Observable.just(it.first)
                        }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return Observable.just(Any() as T)
    }
}