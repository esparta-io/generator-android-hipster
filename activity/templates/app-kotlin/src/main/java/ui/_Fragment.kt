package <%= appPackage %>.ui.<%= activityPackageName %>

import <%= appPackage %>.ui.base.BaseFragment
import <%= appPackage %>.ui.base.EmptyPresenter
import <%= appPackage %>.R
<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.components.<%= activityName %>Component<% } else if (componentType == 'useApplication') { %>import <%= appPackage %>.di.components.ApplicationComponent<% } else {  %>import <%= appPackage %>.di.components.<%= UseExistingComponentName %>Component<% } %>

class <%= activityName %>Fragment : BaseFragment<EmptyPresenter>() {

    override protected fun inject() {
        getComponent(<% if (componentType == 'createNew') { %><%= activityName %>Component<% } else if (componentType == 'useApplication') { %>ApplicationComponent<% } else {  %><%= useExistingComponentName %>Component<% } %>::class.java).inject(this)
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_<%= underscoreActivityName %>
    }

}
