package <%= appPackage %>.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import <%= appPackage %>.application.App
<% if (nucleus == true) { %>import nucleus.view.NucleusAppCompatActivity<% } else { %>import android.support.v7.app.AppCompatActivity<% } %>
<% if (butterknife == true) { %>import butterknife.ButterKnife <% } %>
<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper<% } %>

abstract class BaseActivity<P : BasePresenter<*>?> : <% if (nucleus == true) { %>NucleusAppCompatActivity<P><% } else { %>AppCompatActivity<% } %>() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        injectModule()
        <% if (butterknife == true) { %>ButterKnife.bind(this) <% } %>
        <% if (nucleus == true) { %>presenterFactory = presenterFactory<% } %>
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

    <% if (nucleus == false) { %>
    @CallSuper
    override fun onResume() {
        super.onResume()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
    }

    abstract fun getPresenter(): P
    <% } %>

}
