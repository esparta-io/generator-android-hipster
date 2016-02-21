package <%= appPackage %>.ui.<%= activityPackageName %>

import <%= appPackage %>.ui.base.BaseFragment
import <%= appPackage %>.ui.base.EmptyPresenter
import <%= appPackage %>.R
<% if (componentType == 'createNew') { %>
import <%= appPackage %>.di.components.<%= activityName %>Component;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
<% } else if (componentType == 'useApplication') { %>
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.ApplicationComponent;
<% } else {  %>import <%= appPackage %>.di.components.<%= useExistingComponentName %>Component<% } %>

class <%= activityName %>Fragment : BaseFragment<EmptyPresenter>() {

    override protected fun inject() {
        getComponent(<% if (componentType != 'useApplication') { %><%= activityName %>Component<% } else if (componentType == 'useApplication') { %>ApplicationComponent<% } else {  %><%= useExistingComponentName %>Component<% } %>::class.java).inject(this)
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_<%= underscoreActivityName %>
    }

}
