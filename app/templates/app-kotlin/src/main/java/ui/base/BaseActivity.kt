package <%= appPackage %>.ui.base

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import <%= appPackage %>.application.App
import <%= appPackage %>.extensions.makeLogin
import <%= appPackage %>.extensions.registerSyncReceiver
import <%= appPackage %>.service.push.PushExtras
<% if (butterknife == true) { %>import butterknife.ButterKnife <% } %>
<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper<% } %>

abstract class BaseActivity<out P : BasePresenter<*>?> : AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        injectModule()
        <% if (butterknife == true) { %>ButterKnife.bind(this) <% } %>
    }

    protected abstract fun injectModule()

    protected abstract fun getLayoutResource(): Int

    @CallSuper
    override fun onDestroy() {
        <% if (butterknife == true) { %>ButterKnife.unbind(this) <% } %>
        super.onDestroy()
    }

    <% if (calligraphy == true) { %>@CallSuper
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }<% } %>

    @CallSuper
    override fun onResume() {
        super.onResume()
        this.registerSyncReceiver(receiver, PushExtras.UNAUTHORIZED)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(receiver)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (this is IToolbarActivity && handleHomePressed(this, item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun getPresenter(): P

    private val receiver = object : BroadcastReceiver() {
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
