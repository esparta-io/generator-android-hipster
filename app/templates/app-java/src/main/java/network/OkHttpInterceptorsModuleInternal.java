package <%= appPackage %>.network;

import android.support.annotation.NonNull;

<% if (stetho== true) { %>import com.facebook.stetho.okhttp3.StethoInterceptor;
import static java.util.Collections.emptyList;
<% } %>
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
<% if (timber == true) { %>import timber.log.Timber;<% } %>


import static java.util.Collections.singletonList;

@Module
public class OkHttpInterceptorsModule {

    @Provides
    @Singleton
    @NonNull
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> <% if (timber == true) { %>Timber.d(message)<% } %>);
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpInterceptors(@NonNull HttpLoggingInterceptor httpLoggingInterceptor) {
        return singletonList(httpLoggingInterceptor);
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        <% if (stetho == true) { %>return singletonList(new StethoInterceptor());<% } else { %>return emptyList();<% } %>
    }
}