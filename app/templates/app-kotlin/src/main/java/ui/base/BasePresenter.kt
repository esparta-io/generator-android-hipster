package <%= appPackage %>.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import timber.log.Timber
import java.util.ArrayList
import <%= appPackage %>.ui.base.BaseViewCoroutineScope

abstract class BasePresenter<V : PresenterView> : Presenter<V>(), BaseViewCoroutineScope, LifecycleObserver {

    override var job: Job? = null

    private var disposableList = ArrayList<Disposable>()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "Coroutine exception not handled")
        if (view is ProgressPresenterView) {
            (view as ProgressPresenterView).hideProgress()
        }
    }

    fun add(disposable: Disposable) {
        disposableList .add(disposable)
    }

    fun unSubscribe() {
        disposableList
                .filter { ! it.isDisposed }
                .forEach { it.dispose() }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun dropView() {
        super.dropView()
        job?.cancel()
        unSubscribe()
    }

    override fun onTakeView(view: V) {
        super.onTakeView(view)
        createSupervisorJob()
    }
}
