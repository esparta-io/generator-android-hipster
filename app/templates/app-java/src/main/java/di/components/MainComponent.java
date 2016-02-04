package <%= appPackage %>.di.components;

import dagger.Component;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.modules.MainModule;
import <%= appPackage %>.ui.main.MainActivity;
import <%= appPackage %>.ui.main.MainFragment;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity activity);
    void inject(MainFragment fragment);

}
