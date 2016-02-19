package <%= appPackage %>.environment;

import android.app.Application;

import <%= appPackage %>.application.App;
import <%= appPackage %>.BuildConfig;

import javax.inject.Singleton;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.BuildConfig;
import <%= appPackage %>.application.App;
<% if (mixpanel == true) { %>import com.mixpanel.android.mpmetrics.MixpanelAPI;<% } %>

import dagger.Module;
import dagger.Provides;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class EnvironmentModule {

    static final int DISK_CACHE_SIZE = (int) 1_000_000L;

    private final App app;

    @NonNull
    private final ChangeableBaseUrl changeableBaseUrl;

    public EnvironmentModule(App app) {
        this.app = app;
        this.changeableBaseUrl = new ChangeableBaseUrl("");
    }

    @Provides @NonNull @Singleton
    public ChangeableBaseUrl provideChangeableBaseUrl() {
        return changeableBaseUrl;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@ForApplication Context app,
                                     @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                     @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {

        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        okHttpBuilder.cache(cache);
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS);

        return okHttpBuilder.build();

    }

    @Provides
    @Singleton
    public Retrofit provideRestAdapter(@NonNull OkHttpClient okHttpClient, @NonNull ChangeableBaseUrl changeableBaseUrl, @NonNull Gson gson) {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(changeableBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return restAdapter;
    }

    <% if (mixpanel == true) { %>@Provides
    @Singleton
    public MixpanelAPI provideMixpanelApi(@ForApplication Application application) {
    	   MixpanelAPI mixpanel = MixpanelAPI.getInstance(application, "token");
    	   return mixpanel;
    }<% } %>

}
