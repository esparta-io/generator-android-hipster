package <%= appPackage %>.ui.base;

import <%= appPackage %>.application.App;
import android.os.Bundle;
import android.content.Context;
import android.support.annotation.CallSuper;
<% if (nucleus == true) { %>import nucleus.view.NucleusAppCompatActivity;<% } else { %>import android.support.v7.app.AppCompatActivity;<% } %>
<% if (butterknife == true) { %>import butterknife.Bind;
import butterknife.ButterKnife; <% } %>
import com.squareup.leakcanary.RefWatcher;
<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;<% } %>

public abstract class BaseActivity<P extends BasePresenter> extends <% if (nucleus == true) { %>NucleusAppCompatActivity<P><% } else { %>AppCompatActivity<% } %> {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        injectModule();
        <% if (butterknife == true) { %>ButterKnife.bind(this); <% } %>
        <% if (nucleus == true) { %>setPresenterFactory(getPresenterFactory()); <% } else { %>getPresenter().onDestroy();<% } %>
    }

    protected abstract void injectModule();

    protected abstract int getLayoutResource();

    @CallSuper
    @Override
    public void onDestroy() {
        <% if (butterknife == true) { %>ButterKnife.unbind(this); <% } %>
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher();
        refWatcher.watch(this);
    }

    <% if (calligraphy == true) { %>@CallSuper
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }<% } %>


    <% if (nucleus == false) { %>
    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onTakeView(this);
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onDropView();
    }

    protected abstract P getPresenter();
    <% } %>
}
