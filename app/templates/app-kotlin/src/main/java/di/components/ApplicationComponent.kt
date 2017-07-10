package <%= appPackage %>.di.components

import android.content.Context
import com.google.gson.Gson
import dagger.Component
import <%= appPackage %>.application.App
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.di.modules.AndroidModule
import <%= appPackage %>.di.modules.ApplicationModule
import <%= appPackage %>.domain.executors.ThreadExecutor
import <%= appPackage %>.di.components.DaggerApplicationComponent
import <%= appPackage %>.network.OkHttpInterceptorsModule
import <%= appPackage %>.environment.EnvironmentModule
import <%= appPackage %>.storage.Storage
import <%= appPackage %>.util.gson.GsonModule
import <%= appPackage %>.di.modules.UserModule
import retrofit2.Retrofit
import io.reactivex.Scheduler
import javax.inject.Singleton

<% if(eventbus==true) { %>import org.greenrobot.eventbus.EventBus<% } %>

// android-hipster-needle-component-injection-import

@Singleton
@Component(modules = arrayOf(
                            ApplicationModule::class,
                            AndroidModule::class,
                            OkHttpInterceptorsModule::class,
                            GsonModule::class,
                            EnvironmentModule::class))
interface ApplicationComponent {

    fun provideThreadExecutor(): Scheduler

    fun provideStorage(): Storage

    fun provideRetrofit(): Retrofit

    @ForApplication
    fun provideContext(): Context

    fun provideGson(): Gson

    fun inject(app: App)

    fun plus(userModule: UserModule): UserComponent?

    <% if(eventbus==true) { %>fun provideEventBus(): EventBus<% } %>

    // android-hipster-needle-component-injection-method

    object Initializer {
        fun init(app: App): ApplicationComponent {
            return DaggerApplicationComponent.builder().androidModule(AndroidModule()).okHttpInterceptorsModule(OkHttpInterceptorsModule()).gsonModule(GsonModule()).applicationModule(ApplicationModule(app)).environmentModule(EnvironmentModule(app)).build()
        }
    }

}
