package <%= appPackage %>.ui.base

import <%= appPackage %>.di.HasComponent

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

<% if (nucleus == true) { %>import nucleus.view.NucleusSupportFragment<% } else { %>import android.support.v4.app.Fragment<% } %>
<% if (butterknife == true) { %>import butterknife.Bind
import butterknife.ButterKnife <% } %>

abstract class BaseFragment<P : BasePresenter<*>> : <% if (nucleus == true) { %>NucleusSupportFragment<P><% } else { %>Fragment<% } %>() {

    @CallSuper
    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View  {
        val rootView = inflater!!.inflate(getLayoutResource(), container, false)
        <% if (butterknife == true) { %>ButterKnife.bind(this, rootView)<% } %>
        return rootView;
    }

    override fun onCreate(bundle: Bundle? ) {
        super.onCreate(bundle);
        inject();
        <% if (nucleus == true) { %>presenterFactory = presenterFactory<% } %>
    }

    @CallSuper
    override fun onDestroyView() {
        <% if (butterknife == true) { %>ButterKnife.unbind(this)<% } %>
        super.onDestroyView()
        <% if (nucleus == false) { %>getPresenter().destroy()<% } %>
    }

    fun getBaseActivity() : BaseActivity<*> {
        return activity as BaseActivity<*>;
    }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context);
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<*>).getComponent())
    }

    protected abstract fun inject()

    protected abstract fun getLayoutResource(): Int

    <% if (nucleus == false) { %>protected abstract fun getPresenter(): P<% } %>

}
