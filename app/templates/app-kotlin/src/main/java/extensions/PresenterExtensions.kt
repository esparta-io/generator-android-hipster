package <%= appPackage %>.extensions

import <%= appPackage %>.ui.base.BasePresenter
import <%= appPackage %>.ui.base.PresenterView
import nucleus.presenter.RxPresenter
import rx.Observable
import rx.Single
import rx.functions.Action1
import rx.lang.kotlin.toSingletonObservable

fun <V : PresenterView, R> BasePresenter<V>.justView(observable: Observable<R>, delivered: (V) -> Unit, deliveredError: (view: V, result: Throwable) -> Unit, throwable: (t: Throwable) -> Unit) {
    add(deliverFirst<R>().call(observable).subscribe(split({ v, o ->
        delivered(v)
    }) { v, t ->
        deliveredError(v, t)
    }, Action1 {
        throwable(it)
    }))
}

fun <V : PresenterView, R> BasePresenter<V>.singleView(single: Single<R>, delivered: (V) -> Unit, deliveredError: (view: V, result: Throwable) -> Unit, throwable: (t: Throwable) -> Unit) {
    add(deliverFirst<R>().call(single.toObservable()).subscribe(split({ v, o ->
        delivered(v)
    }) { v, t ->
        deliveredError(v, t)
    }, Action1 {
        throwable(it)
    }))
}

fun <V : PresenterView> BasePresenter<V>.justView(delivered: (view: V) -> Unit, deliveredError: (view: V, error: Throwable) -> Unit = { v, s -> }, throwable: (Throwable) -> Unit = {}) {
    justView(toSingletonObservable<Any>(), delivered, deliveredError, throwable)
}

fun <V : PresenterView> BasePresenter<V>.singleView(delivered: (view: V) -> Unit, deliveredError: (view: V, error: Throwable) -> Unit = { v, s -> }, throwable: (Throwable) -> Unit = {}) {
    singleView(Single.just(this), delivered, deliveredError, throwable)
}

fun <V : PresenterView, R> BasePresenter<V>.compose(observable: Observable<R>, delivered: (V, R) -> Unit, deliveredError: (view: V, result: Throwable) -> Unit = { v, t -> t.printStackTrace() }, throwable: (t: Throwable) -> Unit = { t -> t.printStackTrace() }) {
    add(deliverFirst<R>().call(observable).subscribe(split({ v, r ->
        delivered(v, r)
    }) { v, t ->
        deliveredError(v, t)
    }, Action1 {
        throwable(it)
    }))
}
