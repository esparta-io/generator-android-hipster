package <%= appPackage %>.ui.main

import android.os.Bundle
import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.di.HasComponent
import <%= appPackage %>.ui.base.BaseActivity
import <%= appPackage %>.R
import <%= appPackage %>.application.App
import <%= appPackage %>.di.components.DaggerMainComponent
import <%= appPackage %>.di.components.MainComponent
import <%= appPackage %>.di.modules.MainModule

<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory <% } %>

import javax.inject.Inject

@ActivityScope
class MainActivity : BaseActivity<MainPresenter>(), MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    lateinit var component: MainComponent

    override fun injectModule() {
      component = DaggerMainComponent.builder().applicationComponent(App.graph).mainModule(MainModule(this)).build()
      component.inject(this)
    }

    <% if (nucleus == true) { %>override fun getPresenterFactory(): PresenterFactory<MainPresenter>? = PresenterFactory { mainPresenter }<% } %>

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
    }

    override fun getLayoutResource(): Int {
      return R.layout.activity_main
    }

}
