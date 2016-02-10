package <%= appPackage %>.ui.<%= fragmentPackageName %>;

import android.support.v4.app.Fragment;

import javax.inject.Inject;

<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.components.<%= activityName %>Component;<% } else if (componentType == 'useApplication') { %>import <%= appPackage %>.application.App;<% } %>
<% if (usePresenter == false) { %>import <%= appPackage %>.ui.base.EmptyPresenter;<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>
import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.R;


public class <%= fragmentName %>Fragment extends BaseFragment<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter> <% if (usePresenter) { %>implements <%= fragmentName %>View<% } %> {

    @Inject
    <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter;

    @Override
    protected void inject() {
        <% if (componentType == 'createNew') { %>getComponent(<%= activityName %>Component.class).inject(this);<% } else { %>App.graph.inject(this);<% } %>
    }

    <% if (nucleus == true) { %>
    public PresenterFactory<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter> getPresenterFactory() {
        return () -> <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter;
    }<% } else { %>
    @Override
    protected <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter getPresenter() {
        return <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter;
    }<% } %>

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_<%= underscoreFragmentName %>;
    }

    public static Fragment newInstance() {
        return new <%= fragmentName %>Fragment();
    }

}
