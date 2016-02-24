package <%= appPackage %>.network;

import com.facebook.stetho.okhttp3.StethoInterceptor

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
<% if (timber == true) { %>import timber.log.Timber<% } %>

@Module
class OkHttpInterceptorsModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> <% if (timber == true) { %>Timber.d(message)<% } %> }
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    fun provideOkHttpInterceptors(httpLoggingInterceptor: HttpLoggingInterceptor): Array<Interceptor> {
        return arrayOf(httpLoggingInterceptor)
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    fun provideOkHttpNetworkInterceptors(): Array<Interceptor> {
        return arrayOf(StethoInterceptor())
    }
}