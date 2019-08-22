package <%= appPackage %>.ui.main

import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.ui.base.BasePresenter

import javax.inject.Inject

@Suppress("EmptyClassBlock")
@ActivityScope
class MainPresenter @Inject constructor() : BasePresenter<MainView>() {


}
