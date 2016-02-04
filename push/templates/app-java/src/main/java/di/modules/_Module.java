package <%= appPackage %>.di.modules;

import dagger.Module;
import dagger.Provides;
import <%= appPackage %>.ui.<%= activityPackageName %>.<%= activityName %>Activity;


@Module
public class <%= activityName %>Module extends ActivityModule {

  public <%= activityName %>Module(<%= activityName %>Activity activity) {
    super(activity);
  }

}
