package <%= appPackage %>.ui.<%= activityPackageName %>;

import android.support.v4.app.Fragment;

<% if (componentType == 'createNew') { %>
import <%= appPackage %>.di.components.<%= activityName %>Component;
<% } else if (componentType == 'useApplication') { %>
import <%= appPackage %>.application.App;
<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>

import javax.inject.Inject;

import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.ui.base.EmptyPresenter;
import <%= appPackage %>.R;


public class <%= activityName %>Fragment extends BaseFragment<EmptyPresenter>  {

    @Inject
    EmptyPresenter emptyPresenter;

    @Override
    protected void inject() {
        <% if (componentType == 'createNew') { %>getComponent(<%= activityName %>Component.class).inject(this);<% } else { %>App.graph.inject(this);<% } %>
    }

    <% if (nucleus == true) { %>
    public PresenterFactory<EmptyPresenter> getPresenterFactory() {
        return () -> emptyPresenter;
    }<% } %>
    <% if (nucleus == true) { %>
    public PresenterFactory<EmptyPresenter> getPresenterFactory() {
        return () -> emptyPresenter;
    }<% } else { %>
    @Override
    protected EmptyPresenter getPresenter() {
        return emptyPresenter;
    }<% } %>


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_<%= underscoreActivityName %>;
    }

    public static Fragment newInstance() {
        return new <%= activityName %>Fragment();
    }
}
