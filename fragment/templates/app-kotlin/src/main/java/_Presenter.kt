package <%= appPackage %>.ui.<%= fragmentPackageName %>;

<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.ActivityScope;<% } %>
<% if (componentType == 'useApplication') { %>import javax.inject.Singleton;<% } %>
import <%= appPackage %>.ui.base.BasePresenter;

import javax.inject.Inject;

<% if (componentType == 'createNew') { %>@ActivityScope<% } else if (componentType == 'useApplication') { %>@Singleton<% } %>
class <%= fragmentName %>Presenter @Inject constructor() : BasePresenter<<%= fragmentName %>View>() {

}
