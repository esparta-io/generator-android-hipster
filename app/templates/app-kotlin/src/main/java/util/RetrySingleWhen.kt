package <%= appPackage %>.util

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 *
 * Created by gmribas on 30/04/18.
 */
class RetrySingleWhen<T>: SingleTransformer<T, T> {

    private var count by Delegates.notNull<Int>()
    private var period by Delegates.notNull<Int>()
    private var backoff by Delegates.notNull<Int>()

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

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.retryWhen {
            it.cast(Throwable::class.java)
                    .zipWith(1..count) { throwable, counter -> Pair(throwable, counter) }
                    .flatMap {
                        if (it.second < count)
                            Flowable.timer((period + period * backoff * count).toLong(), TimeUnit.MILLISECONDS)
                        else
                            Flowable.error(it.first)
                    }
        }
    }
}