package  <%= appPackage %>.di.modules;

import dagger.Module;
import dagger.Provides;
import  <%= appPackage %>.di.ActivityScope;
import  <%= appPackage %>.ui.base.BaseActivity;

@ActivityScope
@Module
public class ActivityModule {

  protected BaseActivity activity;

  public ActivityModule(BaseActivity activity) {
    this.activity = activity;
  }

  @Provides
  protected BaseActivity activity() {
    return activity;
  }
}
