package <%= appPackage %>.di.components;

import javax.inject.Singleton;

import dagger.Component;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.modules.AndroidModule;
import <%= appPackage %>.di.modules.ApplicationModule;
import <%= appPackage %>.util.gson.GsonModule;
import <%= appPackage %>.environment.EnvironmentModule;

// android-hipster-needle-component-injection-import

@Singleton
@Component(modules = {ApplicationModule.class, AndroidModule.class, GsonModule.class, EnvironmentModule.class})
public interface ApplicationComponent {

  void inject(App app);

  // android-hipster-needle-component-injection-method

}
