package <%= appPackage %>.di.components;

import dagger.Component;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity;
<% if (fragment == true) { %>import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Fragment;<% } %>

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {<%= activityName %>Module.class})
public interface <%= activityName %>Component {

    void inject(<%= activityName %>Activity activity);
    <% if (fragment == true) { %>void inject(<%= activityName %>Fragment fragment); <% } %>

}
