package <%= appPackage %>.di;

import <%= appPackage %>.application.TestApp;
import <%= appPackage %>.di.components.ApplicationComponent;
import <%= appPackage %>.di.modules.AndroidModule;
import <%= appPackage %>.di.modules.ApplicationModule;
import <%= appPackage %>.di.modules.MCSModule;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.network.OkHttpInterceptorsModule;
import <%= appPackage %>.ui.MCSTest;
import <%= appPackage %>.ui.MainActivityTest;
import <%= appPackage %>.util.gson.GsonModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidModule.class,
        GsonModule.class,
        MCSModule.class,
        OkHttpInterceptorsModule.class,
        EnvironmentModule.class}
)
public interface MockApplicationComponent extends ApplicationComponent {

    void inject(MainActivityTest mainActivityTest);

    void inject(MCSTest mcsTest);

    final class Initializer {

        private Initializer() {

        }

        public static MockApplicationComponent init(TestApp app) {
            return DaggerMockApplicationComponent.builder()
                    .androidModule(new AndroidModule())
                    .gsonModule(new GsonModule())
                    .mCSModule(new MCSModule())
                    .applicationModule(new ApplicationModule(app))
                    .environmentModule(new EnvironmentModule(app))
                    .build();
        }
    }

}
