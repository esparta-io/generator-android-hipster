package <%= appPackage %>.ui.<%= activityPackageName %>;

<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.ActivityScope;<% } %>
import <%= appPackage %>.ui.base.BasePresenter;

import javax.inject.Inject;
<% if (componentType == 'useApplication') { %>import javax.inject.Singleton;<% } %>
<% if (componentType == 'useExistingComponent') { %>import javax.inject.Singleton;<% } %>

<% if (componentType == 'createNew') { %>@ActivityScope<% } else { %>@Singleton<% } %>
public class <%= activityName %>Presenter extends BasePresenter<<%= activityName %>View> {

    @Inject
    public <%= activityName %>Presenter() {

    }

}
