package <%= appPackage %>.di.components;

import javax.inject.Singleton;

import dagger.Component;
import <%= appPackage %>.application.App;
import <%= appPackage %>.util.gson.GsonModule;
import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.R;
import <%= appPackage %>.di.components.ApplicationComponent;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.di.components.DaggerApplicationComponent;
import <%= appPackage %>.di.modules.AndroidModule;
import <%= appPackage %>.di.modules.ApplicationModule;
import <%= appPackage %>.environment.EnvironmentConfiguration;
import <%= appPackage %>.util.gson.GsonModule;



import android.content.Context;

import <%= appPackage %>.domain.executors.ThreadExecutor;
import <%= appPackage %>.storage.Storage;
import com.google.gson.Gson;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
// android-hipster-needle-component-injection-import

@Singleton
@Component(modules = {ApplicationModule.class, AndroidModule.class, GsonModule.class, EnvironmentModule.class})
public interface ApplicationComponent {

    ThreadExecutor provideThreadExecutor();

    Storage provideStorage();

    Retrofit provideRetrofit();

    @ForApplication
    Context provideContext();

    Gson provideGson();

    void inject(App app);

  // android-hipster-needle-component-injection-method

    final class Initializer {
          public static ApplicationComponent init(App app) {
              return DaggerApplicationComponent.builder()
                            .androidModule(new AndroidModule())
                            .gsonModule(new GsonModule())
                            .applicationModule(new ApplicationModule(app))
                            .environmentModule(new EnvironmentModule(app))
                            .build();
          }
      }

}
