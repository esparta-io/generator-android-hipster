package <%= appPackage %>.di.modules

import dagger.Module
import dagger.Provides
import  <%= appPackage %>.ui.main.MainActivity

@Module
public class MainModule(val activity: MainActivity) {

    @Provides
    fun provideActivity() : MainActivity{
        return activity
    }

}
