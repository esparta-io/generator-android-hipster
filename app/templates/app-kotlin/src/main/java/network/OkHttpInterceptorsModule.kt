package <%= appPackage %>.network;

import android.support.annotation.NonNull
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor


@Module
class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    fun provideOkHttpInterceptors(): Array<Interceptor> {
        return emptyArray()
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    fun provideOkHttpNetworkInterceptors(): Array<Interceptor> {
        return emptyArray()
    }
}