package <%= appPackage %>.di.components

import javax.inject.Singleton

import dagger.Component
import <%= appPackage %>.application.App
import <%= appPackage %>.di.modules.AndroidModule
import <%= appPackage %>.di.modules.ApplicationModule
import <%= appPackage %>.util.gson.GsonModule


@Singleton
@Component(modules = arrayOf(ApplicationModule::class, AndroidModule::class, GsonModule::class))
interface ApplicationComponent {

  fun inject(app: App)

}
