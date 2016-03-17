package <%= appPackage %>.ui.main

import android.os.Bundle
import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.di.components.MainComponent;
import <%= appPackage %>.di.HasComponent
import <%= appPackage %>.ui.base.BaseActivity
import <%= appPackage %>.R
import <%= appPackage %>.application.App
import <%= appPackage %>.di.components.DaggerMainComponent
import <%= appPackage %>.di.modules.MainModule

<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory <% } %>

import javax.inject.Inject

@ActivityScope
class MainActivity : BaseActivity<MainPresenter>(), MainView, HasComponent<MainComponent>{

    @Inject
    lateinit var mainPresenter: MainPresenter

    lateinit var mainComponent: MainComponent

    override fun injectModule() {
        mainComponent = DaggerMainComponent.builder().applicationComponent(App.get(this).getComponent()).mainModule(MainModule(this)).build()
        mainComponent.inject(this)
    }

    <% if (nucleus == true) { %>override fun getPresenterFactory(): PresenterFactory<MainPresenter>? = PresenterFactory { mainPresenter }<% } else { %>
    override fun getPresenter(): MainPresenter {
        return mainPresenter
    }<% } %>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun getComponent(): MainComponent {
        return mainComponent
    }

    <% if (nucleus == false) { %>
    override fun onResume() {
        super.onResume()
        getPresenter().takeView(this)
    }

    override fun onPause() {
        super.onPause()
        getPresenter().dropView()
    }<% } %>
}
