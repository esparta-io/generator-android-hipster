package <%= appPackage %>.di.modules;

import dagger.Module;
import dagger.Provides;
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity;

// android-hipster-needle-module-provides-import

@Module
public class <%= activityName %>Module extends ActivityModule {

    public <%= activityName %>Module(<%= activityName %>Activity activity) {
        super(activity);
    }

    // android-hipster-needle-module-provides-method

}
