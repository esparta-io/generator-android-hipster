package <%= appPackage %>.ui.base

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.MenuItem
import <%= appPackage %>.extensions.lazyUnsafe
import android.support.v7.app.AppCompatActivity
import <%= appPackage %>.application.App
import <%= appPackage %>.extensions.makeLogin
import <%= appPackage %>.extensions.registerSyncReceiver
import <%= appPackage %>.service.push.PushExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<out P : BasePresenter<*>> : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    protected val coroutineActivityContext: CoroutineContext by lazyUnsafe { Dispatchers.Main + job }

    override val coroutineContext: CoroutineContext
        get() = coroutineActivityContext

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        job = Job()
        injectModule()
    }

    protected abstract fun injectModule()

    protected abstract fun getLayoutResource(): Int

    override fun onResume() {
        super.onResume()
        lifecycle.addObserver(getPresenter())
        this.registerSyncReceiver(receiver, PushExtras.UNAUTHORIZED)
    }

    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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

            if (intent.action == PushExtras.UNAUTHORIZED && shouldProcessLogout()) {
                makeLogin()
                return
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
