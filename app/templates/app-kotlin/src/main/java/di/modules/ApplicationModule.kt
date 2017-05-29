package <%= appPackage %>.di.modules

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import android.content.SharedPreferences
import com.google.gson.Gson

<% if (eventbus) { %>import org.greenrobot.eventbus.EventBus<% } %>
import dagger.Module
import dagger.Provides
import <%= appPackage %>.application.App
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.domain.executors.JobExecutor
import <%= appPackage %>.domain.executors.ThreadExecutor
import <%= appPackage %>.storage.Storage

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
// android-hipster-needle-module-provides-import


@Module
class ApplicationModule(val application: App) {

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
    fun provideThreadExecutor(): Scheduler {
        return Schedulers.from(JobExecutor());
    }

    @Provides
    @Singleton
    fun provideStorage(gson: Gson, preferences: SharedPreferences): Storage {
        return Storage(preferences, gson)
    }

    <% if (eventbus) { %>
    @Provides
    @Singleton
    fun provideBus(): EventBus {
        return EventBus.getDefault();
    }
    <% } %>

    // android-hipster-needle-module-provides-method
}
