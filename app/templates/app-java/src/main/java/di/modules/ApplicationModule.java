package <%= appPackage %>.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;
import com.google.gson.Gson;
<% if (eventbus) { %>import org.greenrobot.event.EventBus;<% } %>
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.storage.Storage;
import <%= appPackage %>.domain.executors.JobExecutor;
import <%= appPackage %>.domain.executors.ThreadExecutor;

// android-hipster-needle-module-provides-import

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
    Storage provideStorage(Gson gson, SharedPreferences preferences) {
        return new Storage(preferences, gson);
    }

    <% if (eventbus) { %>@Provides
    @Singleton
    EventBus provideBus() {
        return EventBus.getDefault();
    }<% } %>


    // android-hipster-needle-module-provides-method

}
