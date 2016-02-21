package <%= appPackage %>.ui.<%= fragmentPackageName %>;

import android.support.v4.app.Fragment;

import javax.inject.Inject;

<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.components.<%= activityName %>Component;<% } else if (componentType == 'useApplication') { %>import <%= appPackage %>.application.App;<% } %>
<% if (usePresenter == false) { %>import <%= appPackage %>.ui.base.EmptyPresenter;<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>
import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.R;


class <%= fragmentName %>Fragment : BaseFragment<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter>() <% if (usePresenter) { %>, <%= fragmentName %>View<% } %> {

    @Inject
    lateinit var <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter : <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter

    @Override
    override fun inject() {
        <% if (componentType != 'useApplication') { %>getComponent(<%= activityName %>Component::class.java).inject(this)<% } else { %>App.get(context).getComponent().inject(this)<% } %>
    }

    <% if (nucleus == true) { %>
    override fun getPresenterFactory(): PresenterFactory<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter>? = PresenterFactory { <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter }
    <% } else { %>
    @Override
    override fun getPresenter() : <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter {
        return <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter
    }<% } %>

    override fun getLayoutResource(): Int {
        return R.layout.fragment_<%= underscoreFragmentName %>
    }

}
