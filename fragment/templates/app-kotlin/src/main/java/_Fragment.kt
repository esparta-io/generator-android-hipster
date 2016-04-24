package <%= appPackage %>.ui.<%= fragmentPackageName %>;

import javax.inject.Inject;

<% if (componentType == 'createNewSub') { %>import <%= appPackage %>.di.components.<%= useExistingComponentName %><% } else if (componentType == 'createNew') { %>import <%= appPackage %>.di.components.<%= activityName %>Component<% } else if (componentType == 'useApplication') { %>import <%= appPackage %>.application.App;<% } else { %>import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.<%= useExistingComponentName %>Component<% } %>
<% if (usePresenter == false) { %>import <%= appPackage %>.ui.base.EmptyPresenter;<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>
import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.R;


class <%= fragmentName %>Fragment : BaseFragment<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter>() <% if (usePresenter) { %>, <%= fragmentName %>View<% } %> {

    @Inject
    lateinit var <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter : <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter

    @Override
    override fun inject() {
        <% if (componentType == 'createNewSub') { %>getComponent(<%= useExistingComponentName %>::class.java).inject(this)<% } else if (componentType == 'createNew') { %>getComponent(<%= activityName %>Component::class.java).inject(this)<% } else if (componentType == 'useApplication') { %>App.get(context).getComponent().inject(this)<% } else { %>App.get(context).get<%= useExistingComponentName.replace('Application', '') %>Component().inject(this)<% } %>
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

    @Override
    override fun onResume() {
        super.onResume()
        <% if (nucleus == false) { %>presenter.onTakeView(this)<% } %>
    }

    @Override
    override fun onPause() {
        super.onPause()
        <% if (nucleus == false) { %>presenter.onDropView()<% } %>
    }

}
