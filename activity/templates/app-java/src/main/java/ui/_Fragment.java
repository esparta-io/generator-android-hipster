package <%= appPackage %>.ui.<%= activityPackageName %>;

<% if (componentType == 'createNew') { %>
import <%= appPackage %>.di.components.<%= activityName %>Component;
<% } else if (componentType == 'useApplication') { %>
import <%= appPackage %>.application.App;
<% } %>

import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.ui.base.EmptyPresenter;
import <%= appPackage %>.R;


public class <%= activityName %>Fragment extends BaseFragment<EmptyPresenter> {

  @Override
  protected void inject() {
    <% if (componentType == 'createNew') { %>getComponent(<%= activityName %>Component.class).inject(this);<% } else { %>App.graph.inject(this);<% } %>
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.fragment_<%= activityName.toLowerCase() %>;
  }
}
