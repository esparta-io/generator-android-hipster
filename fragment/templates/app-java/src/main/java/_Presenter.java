package <%= appPackage %>.ui.<%= fragmentPackageName %>;

<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.ActivityScope;<% } %>
import <%= appPackage %>.ui.base.BasePresenter;

import javax.inject.Inject;
<% if (componentType == 'useApplication') { %>import javax.inject.Singleton;<% } %>

<% if (componentType == 'createNew') { %>@ActivityScope<% } else { %>@Singleton<% } %>
public class <%= fragmentName %>Presenter extends BasePresenter<<%= fragmentName %>View> {

    @Inject
    public <%= fragmentName %>Presenter() {

    }

}
