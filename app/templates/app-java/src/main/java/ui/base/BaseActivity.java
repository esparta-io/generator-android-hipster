package <%= appPackage %>.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
<% if (nucleus == true) { %>import nucleus.view.NucleusAppCompatActivity;<% } else { %>import android.support.v7.app.AppCompatActivity;<% } %>
<% if (butterknife == true) { %>import butterknife.Bind;
import butterknife.ButterKnife; <% } %>

public abstract class BaseActivity<P extends BasePresenter> extends <% if (nucleus == true) { %>NucleusAppCompatActivity<P><% } else { %>AppCompatActivity;<% } %> {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        injectModule();

        <% if (butterknife == true) { %>ButterKnife.bind(this); <% } %>
        <% if (nucleus == true) { %> setPresenterFactory(getPresenterFactory()); <% } %>
    }

    protected abstract void injectModule();

    protected abstract int getLayoutResource();

    @CallSuper
    @Override
    public void onDestroy() {
    <% if (butterknife == true) { %>ButterKnife.unbind(this); <% } %>
        super.onDestroy();
    }
}
