package <%= appPackage %>.di.components;

import dagger.Component;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity;

// android-hipster-needle-component-injection-import


@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {<%= activityName %>Module.class})
public interface <%= activityName %>Component {

    void inject(<%= activityName %>Activity activity);

    // android-hipster-needle-component-injection-method

}
