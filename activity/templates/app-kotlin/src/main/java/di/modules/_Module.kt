package <%= appPackage %>.di.modules

import dagger.Module
import dagger.Provides
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity

// android-hipster-needle-module-provides-import


@Module
class <%= activityName %>Module(val activity: <%= activityName %>Activity) {

    @Provides
    fun provideActivity() : <%= activityName %>Activity{
        return activity
    }

    // android-hipster-needle-module-provides-method

}
