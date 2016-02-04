package <%= appPackage %>.di.components

import dagger.Component;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity;
<% if (fragment == true) { %>import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Fragment;<% } %>

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(<%= activityName %>Module::class))
interface <%= activityName %>Component {

  fun inject(activity: <%= activityName %>Activity)
  <% if (fragment == true) { %>fun inject(fragment: <%= activityName %>Fragment)<% } %>

}
