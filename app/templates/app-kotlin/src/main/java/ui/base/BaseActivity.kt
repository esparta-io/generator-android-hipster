package <%= appPackage %>.ui.base

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import android.view.MenuItem
import <%= appPackage %>.extensions.lazyUnsafe
import androidx.appcompat.app.AppCompatActivity
import <%= appPackage %>.application.App
import <%= appPackage %>.extensions.makeLogin
import <%= appPackage %>.extensions.registerSyncReceiver
import <%= appPackage %>.service.push.PushExtras
import <%= appPackage %>.service.BroadcastExtras
import <%= appPackage %>.ui.base.BaseViewCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<out P : BasePresenter<*>> : AppCompatActivity(), BaseViewCoroutineScope, PresenterView {

    override var job: Job? = null

    private lateinit var coroutineActivityContext: CoroutineContext

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        createSupervisorJob()
        injectModule()
    }

    protected abstract fun injectModule()

    protected abstract fun getLayoutResource(): Int

    protected open fun checkIfIsSynchronizing(): Boolean = true

    protected open fun processSynchronized() {}

    protected open fun processSynchronizingProblems() {}

    override fun onResume() {
        super.onResume()
        lifecycle.addObserver(getPresenter())
        this.registerSyncReceiver(receiver,
                PushExtras.UNAUTHORIZED,
                PushExtras.BROADCAST_NOTIFICATION,
                BroadcastExtras.SYNCHRONIZING,
                BroadcastExtras.SYNCHRONIZED,
                BroadcastExtras.SYNCHRONIZING_PROBLEMS)

//        if (checkIfIsSynchronizing() && storage.getBoolean(SyncExecutor.IS_SYNCHRONIZING)) {
//            startActivity(intentFor<SynchronizingActivity>())
//        }
    }

    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        lifecycle.removeObserver(getPresenter())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (this is IToolbarActivity && handleHomePressed(this, item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun getPresenter(): P

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.action) {
                PushExtras.UNAUTHORIZED -> {
                    if (shouldProcessLogout()) {
                        makeLogin()
                    }
                }

                BroadcastExtras.SYNCHRONIZING -> {}

                BroadcastExtras.SYNCHRONIZED -> processSynchronized()

                BroadcastExtras.SYNCHRONIZING_PROBLEMS -> processSynchronizingProblems()
            }

            val notification = intent.getBundleExtra(PushExtras.PUSH)
            if (processPush(notification)) {
                abortBroadcast()
            }
        }
    }

    protected open fun shouldProcessLogout(): Boolean = true

    protected open fun processPush(notification: Bundle?): Boolean = false

}