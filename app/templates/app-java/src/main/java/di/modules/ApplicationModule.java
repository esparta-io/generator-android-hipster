package <%= appPackage %>.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;
import com.google.gson.Gson;
<% if (eventbus) { %>import org.greenrobot.eventbus.EventBus;<% } %>
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.storage.Storage;
import <%= appPackage %>.domain.executors.JobExecutor;
import <%= appPackage %>.domain.executors.ThreadExecutor;


import rx.Scheduler;
import rx.schedulers.Schedulers;
<% if (picasso) { %>
import okhttp3.OkHttpClient;
import com.squareup.picasso.Picasso;<% } %>

// android-hipster-needle-module-provides-import

@Module
public class ApplicationModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

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
    public App provideApp() {
        return application;
    }

    @Provides
    @Singleton
    public Scheduler provideScheduler() {
        return Schedulers.from(new JobExecutor());
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

    @Provides @NonNull @Named(MAIN_THREAD_HANDLER) @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    <% if (picasso) { %>@Provides @NonNull @Singleton
    public Picasso providePicasso(@NonNull @ForApplication App app, @NonNull OkHttpClient okHttpClient) {
        return new Picasso.Builder(app)
                .build();
    }<% } %>


    // android-hipster-needle-module-provides-method

}
