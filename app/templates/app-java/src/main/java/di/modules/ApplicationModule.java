package <%= appPackage %>.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.BuildConfig;
import <%= appPackage %>.domain.executors.JobExecutor;
import <%= appPackage %>.domain.executors.ThreadExecutor;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Module
public class ApplicationModule {

  protected App application;

  public ApplicationModule(App application) {
    this.application = application;
  }

  @ForApplication
  @Provides
  @Singleton
  public Application provideApplication() {
    return application;
  }

  @ForApplication
  @Provides
  @Singleton
  public Context provideContext() {
    return application.getApplicationContext();
  }

  @Provides
  @Singleton
  public ThreadExecutor provideThreadExecutor() {
    return new JobExecutor();
  }

  @Provides
  @Singleton
  public Retrofit provideRestAdapter() {
    Retrofit restAdapter = new Retrofit.Builder()
      .baseUrl(BuildConfig.API_ENDPOINT)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
    return restAdapter;
  }
}
