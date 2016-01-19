package <%= appPackage %>.di.modules

import android.app.Application
import android.content.Context

import javax.inject.Singleton

<% if (events == 'otto') { %>import com.squareup.otto.Bus<% } %>
<% if (events == 'eventbus') { %>import de.greenrobot.event.EventBus<% } %>
import dagger.Module
import dagger.Provides
import <%= appPackage %>.application.App
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.BuildConfig
import <%= appPackage %>.domain.executors.JobExecutor
import <%= appPackage %>.domain.executors.ThreadExecutor

import retrofit2.GsonConverterFactory
import retrofit2.Retrofit

@Module
public class ApplicationModule(val application: App) {

  @ForApplication
  @Provides
  @Singleton
  fun provideApplication(): Application {
    return application
  }

  @ForApplication
  @Provides
  @Singleton
  fun provideContext(): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  fun provideThreadExecutor(): ThreadExecutor {
    return JobExecutor();
  }

  @Provides
  @Singleton
  fun provideRestAdapter(): Retrofit {
    val restAdapter = Retrofit.Builder()
      .baseUrl(BuildConfig.API_ENDPOINT)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
    return restAdapter
  }

  <% if (events == 'otto') { %>
  @Provides
  @Singleton
  fun providesBus(): Bus {
    return Bus()
  }
  <% } %>

  <% if (events == 'eventbus') { %>
  @Provides
  @Singleton
  fun provideBus(): EventBus {
    return EventBus.getDefault();
  }
  <% } %>
}
