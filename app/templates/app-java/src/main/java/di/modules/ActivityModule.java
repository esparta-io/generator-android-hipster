package <%= appPackage %>.di.modules;

import dagger.Module;
import dagger.Provides;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.ui.base.BaseActivity;

@ActivityScope
@Module
public class ActivityModule {

    protected BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    protected BaseActivity activity() {
        return baseActivity;
    }
}
