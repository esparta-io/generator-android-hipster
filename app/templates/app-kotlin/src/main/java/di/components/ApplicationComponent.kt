package <%= appPackage %>.di.components

import android.content.Context
import com.google.gson.Gson
import dagger.Component
import <%= appPackage %>.application.App
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.di.modules.AndroidModule
import <%= appPackage %>.di.modules.ApplicationModule
import <%= appPackage %>.domain.executors.ThreadExecutor
import <%= appPackage %>.di.components.DaggerApplicationComponent;
import <%= appPackage %>.environment.EnvironmentModule
import <%= appPackage %>.storage.Storage
import <%= appPackage %>.util.gson.GsonModule
import retrofit2.Retrofit
import javax.inject.Singleton

// android-hipster-needle-component-injection-import

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, AndroidModule::class, GsonModule::class, EnvironmentModule::class))
interface ApplicationComponent {

    fun provideThreadExecutor(): ThreadExecutor

    fun provideStorage(): Storage

    fun provideRetrofit(): Retrofit

    @ForApplication
    fun provideContext(): Context

    fun provideGson(): Gson

    fun inject(app: App)

    // android-hipster-needle-component-injection-method

    object Initializer {
        fun init(app: App): ApplicationComponent {
            return DaggerApplicationComponent.builder().androidModule(AndroidModule()).gsonModule(GsonModule()).applicationModule(ApplicationModule(app)).environmentModule(EnvironmentModule(app)).build()
        }
    }

}
