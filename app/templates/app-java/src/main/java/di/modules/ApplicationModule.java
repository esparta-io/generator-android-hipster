package <%= appPackage %>.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;
import com.google.gson.Gson;
<% if (events == 'otto') { %>import com.squareup.otto.Bus;<% } %>
<% if (events == 'eventbus') { %>import de.greenrobot.event.EventBus;<% } %>
import dagger.Module;
import dagger.Provides;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.storage.Storage;
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

  @Provides
  @Singleton
  Storage provideStorage(Gson gson, SharedPreferences preferences) {
    return new Storage(gson, preferences);
  }

  <% if (events == 'otto') { %>
  @Provides
  @Singleton
  Bus provideBus() {
    return new Bus();
  }
  <% } %>

  <% if (events == 'eventbus') { %>
  @Provides
  @Singleton
  EventBus provideBus() {
        return EventBus.getDefault();
        }
  <% } %>
}
