package <%= appPackage %>.ui.<%= fragmentPackageName %>;

import javax.inject.Inject;

<% if (componentType == 'createNew') { %>
import <%= appPackage %>.di.components.<%= activityName %>Component;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
<% } else if (componentType == 'useApplication') { %>
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.ApplicationComponent;
<% } else {  %>
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.<%= useExistingComponentName %>Component;<% } %>

<% if (usePresenter == false) { %>import <%= appPackage %>.ui.base.EmptyPresenter;<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>
import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.R;


public class <%= fragmentName %>Fragment extends BaseFragment<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter> <% if (usePresenter) { %>implements <%= fragmentName %>View<% } %> {

    @Inject
    <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter;

    @Override
    protected void inject() {
        <% if (componentType == 'createNewSub') { %>getComponent(<%= useExistingComponentName %>.class).inject(this);<% } else if (componentType == 'createNew') { %>getComponent(<%= activityName %>Component.class).inject(this);<% } else if (componentType == 'useApplication') { %>App.get(getContext()).getComponent().inject(this);<% } else { %>App.get(getContext()).get<%= useExistingComponentName.replace('Application', '') %>Component().inject(this);<% } %>
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

}
