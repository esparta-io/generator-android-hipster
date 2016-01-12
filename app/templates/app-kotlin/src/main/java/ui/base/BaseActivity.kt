package <%= appPackage %>.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
<% if (nucleus == true) { %>import nucleus.view.NucleusAppCompatActivity;<% } else { %>import android.support.v7.app.AppCompatActivity<% } %>
<% if (butterknife == true) { %>import butterknife.Bind;
  import butterknife.ButterKnife; <% } %>

abstract class BaseActivity<P : BasePresenter<*>?> : <% if (nucleus == true) { %>NucleusAppCompatActivity<P><% } else { %>AppCompatActivity<% } %>() {

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(getLayoutResource())
    injectModule();
    <% if (butterknife == true) { %>ButterKnife.bind(this) <% } %>
    <% if (nucleus == true) { %> presenterFactory = presenterFactory<% } %>

  }

  protected abstract fun injectModule()

  protected abstract fun getLayoutResource(): Int

  @CallSuper
  override fun onDestroy() {
    <% if (butterknife == true) { %>ButterKnife.unbind(this); <% } %>
    super.onDestroy();
  }

}
