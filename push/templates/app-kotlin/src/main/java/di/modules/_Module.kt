package <%= appPackage %>.di.modules

import dagger.Module
import dagger.Provides
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity

@Module
public class <%= activityName %>Module(val activity: <%= activityName %>Activity) {

    @Provides
    fun provideActivity() : <%= activityName %>Activity{
        return activity
    }
        
}
