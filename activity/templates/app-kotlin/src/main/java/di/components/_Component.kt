package <%= appPackage %>.di.components

import dagger.Component;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity;

// android-hipster-needle-component-injection-import

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(<%= activityName %>Module::class))
interface <%= activityName %>Component {

    fun inject(activity: <%= activityName %>Activity)

    // android-hipster-needle-component-injection-method

}
