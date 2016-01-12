package <%= appPackage %>.di.modules;

import dagger.Module;
import dagger.Provides;
import  <%= appPackage %>.ui.main.MainActivity;

@Module
public class MainModule extends ActivityModule {

  public MainModule(MainActivity activity) {
    super(activity);
  }

}
