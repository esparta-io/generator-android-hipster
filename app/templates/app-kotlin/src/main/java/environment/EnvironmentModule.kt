package <%= appPackage %>.environment;

import <%= appPackage %>.network.OkHttpInterceptors
import <%= appPackage %>.network.OkHttpNetworkInterceptors
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.BuildConfig
import <%= appPackage %>.application.App
<% if (mixpanel == true) { %>import com.mixpanel.android.mpmetrics.MixpanelAPI<% } %>

import android.app.Application
import android.content.Context
import android.support.annotation.NonNull
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class EnvironmentModule(val app: App) {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ForApplication app: Context,
                                     @OkHttpInterceptors @NonNull interceptors: Array<Interceptor>,
                                     @OkHttpNetworkInterceptors @NonNull networkInterceptors: Array<Interceptor>): OkHttpClient {

        val cacheDir = File(app.cacheDir, "http")
        val cache = Cache(cacheDir, 1000000)

        val okHttpBuilder = OkHttpClient.Builder()

        for (interceptor in interceptors) {
            okHttpBuilder.addInterceptor(interceptor)
        }

        for (networkInterceptor in networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor)
        }

        okHttpBuilder.cache(cache)
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS)

        return okHttpBuilder.build()

    }

    @Provides
    @Singleton
    fun provideRestAdapter(okHttpClient: OkHttpClient, gson: Gson): Retrofit  {
        val restAdapter = Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT_LOCAL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return restAdapter
    }

    <% if (mixpanel == true) { %>@Provides
    @Singleton
    fun provideMixpanelApi(@ForApplication application: Application): MixpanelAPI{
        val mixpanel = MixpanelAPI.getInstance(application, "token")
        return mixpanel
    }<% } %>

}
