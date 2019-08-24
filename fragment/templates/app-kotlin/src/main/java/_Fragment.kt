package <%= appPackage %>.ui.<%= fragmentPackageName %>

import javax.inject.Inject

<% if (componentType == 'createNewSub') { %>import <%= appPackage %>.di.components.<%= useExistingComponentName %>Component<% } else if (componentType == 'createNew') { %>import <%= appPackage %>.di.components.<%= activityName %>Component<% } else if (componentType == 'useApplication' || componentType == 'useUserComponent') { %>import <%= appPackage %>.application.App<% } else { %>import <%= appPackage %>.application.App
import <%= appPackage %>.di.components.<%= useExistingComponentName %>Component<% } %>
<% if (usePresenter == false) { %>import <%= appPackage %>.ui.base.EmptyPresenter<% } %>
<% if (usePresenter == false) { %>import <%= appPackage %>.ui.base.EmptyView<% } %>
import <%= appPackage %>.ui.base.BaseFragment
import <%= appPackage %>.R

class <%= fragmentName %>Fragment : BaseFragment<<% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter>(), <% if (usePresenter) { %> <%= fragmentName %><% } else { %>Empty<% } %>View {

    @Inject
    lateinit var <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter : <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter

    override fun onResume() {
        super.onResume()
        <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter.takeView(this)
    }

    override fun onPause() {
        super.onPause()
        <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter.dropView()
    }

    override fun inject() {
        <% if (componentType == 'createNewSub') { %>context?.let { getComponent(<%= useExistingComponentName %>Component::class.java).inject(this) }<% } else if (componentType == 'createNew') { %>context?.let { getComponent(<%= activityName %>Component::class.java).inject(this) }<% } else if (componentType == 'useApplication') { %>context?.let { App.get(it).getComponent().inject(this) }<% } else if (componentType == 'useUserComponent') { %>context?.let { App.get(it).getUserComponent()?.inject(this) }<% } else { %>context?.let { App.get(context).get<%= useExistingComponentName.replace('Application', '') %>Component().inject(this) }<% } %>
    }

    override fun getPresenter(): <% if (usePresenter) { %><%= fragmentName %><% } else { %>Empty<% } %>Presenter = <% if (usePresenter) { %><%= fragmentName.charAt(0).toLowerCase()+fragmentName.slice(1) %><% } else { %>empty<% } %>Presenter

    override fun getLayoutResource(): Int = R.layout.fragment_<%= underscoreFragmentName %>

}
