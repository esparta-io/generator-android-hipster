package <%= appPackage %>.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.appcompat.app.AppCompatActivity
import <%= appPackage %>.extensions.lazyUnsafe
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.jetbrains.anko.coroutines.experimental.Ref
import org.jetbrains.anko.coroutines.experimental.asReference
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : PresenterView> : Presenter<V>(), CoroutineScope, LifecycleObserver {

    private lateinit var job: Job

    protected val coroutineMainContext: CoroutineContext by lazyUnsafe { Dispatchers.Main + job }

    override val coroutineContext: CoroutineContext
        get() = coroutineMainContext

    private var disposableList = ArrayList<Disposable>()

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
        job.cancel()
        unSubscribe()
    }

    override fun onTakeView(view: V) {
        super.onTakeView(view)
        job = Job()
    }
}
