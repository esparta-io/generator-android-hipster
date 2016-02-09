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

public abstract class BaseFragment<P : BasePresenter<*>> : <% if (nucleus == true) { %>NucleusSupportFragment<P><% } else { %>Fragment;<% } %>() {

    @CallSuper
    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View  {
        val rootView = inflater!!.inflate(getLayoutResource(), container, false)
        <% if (butterknife == true) { %>ButterKnife.bind(this, rootView)<% } %>
        return rootView;
    }

    override public fun onCreate(bundle: Bundle? ) {
        super.onCreate(bundle);
        inject();
        <% if (nucleus == true) { %>presenterFactory = presenterFactory<% } %>
    }

    @CallSuper
    @Override
    override fun onResume() {
        super.onResume()
        <% if (nucleus == false) { %>presenter.onTakeView(this)<% } % >
    }

    @CallSuper
    @Override
    override fun onPause() {
        super.onPause()
        <% if (nucleus == false) { %>presenter.onDropView()<% } % >
    }

    @CallSuper
    override fun onDestroyView() {
        <% if (butterknife == true) { %>ButterKnife.unbind(this) <% } %>
        super.onDestroyView()
        <% if (nucleus == false) { %>presenter.onDestroy()<% } %>
    }

    public fun getBaseActivity() : BaseActivity<*>  {
        return activity as BaseActivity<*>;
    }

    @CallSuper
    override public fun onAttach(context: Context) {
        super.onAttach(context);
    }

    protected fun <C> getComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<*>).component)
    }

    protected abstract fun inject()

    protected abstract fun getLayoutResource(): Int

    <% if (nucleus == false) { %>protected abstract fun getPresenter(): P<% } %>


}
