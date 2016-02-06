package <%= appPackage %>.ui.base;

import <%= appPackage %>.di.HasComponent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<% if (nucleus == true) { %>import nucleus.view.NucleusSupportFragment;<% } else { %>import android.support.v4.app.Fragment;<% } %>
<% if (butterknife == true) { %>import butterknife.Bind;
import butterknife.ButterKnife; <% } %>

public abstract class BaseFragment<P extends BasePresenter> extends <% if (nucleus == true) { %>NucleusSupportFragment<P><% } else { %>Fragment<% } %> {

    @CallSuper
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        <% if (butterknife == true) { %>ButterKnife.bind(this, rootView);<% } %>

        return rootView;
    }

    @CallSuper
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        inject();
        <% if (nucleus == true) { %>setPresenterFactory(getPresenterFactory());<% } %>
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        <% if (nucleus == false) { %>getPresenter().onTakeView(this);<% } %>
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        <% if (nucleus == false) { %>getPresenter().onDropView();<% } %>
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        <% if (butterknife == true) { %>ButterKnife.unbind(this); <% } %>
        super.onDestroyView();
        <% if (nucleus == false) { %>getPresenter().onDestroy();<% } %>
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected abstract void inject();

    protected abstract int getLayoutResource();

}
