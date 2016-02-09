package <%= appPackage %>.di.components

import dagger.Component
import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.di.modules.MainModule
import <%= appPackage %>.ui.main.MainActivity
import <%= appPackage %>.ui.main.MainFragment

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {

  fun inject(activity: MainActivity)
  fun inject(fragment: MainFragment)

}

