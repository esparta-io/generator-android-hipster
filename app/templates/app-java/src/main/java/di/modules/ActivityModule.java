package <%= appPackage %>.di.modules

import dagger.Module
import dagger.Provides
import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.ui.base.BaseActivity
import <%= appPackage %>.ui.base.BasePresenter
import <%= appPackage %>.ui.base.PresenterView

@ActivityScope
@Module
open class ActivityModule(protected var activity:BaseActivity<BasePresenter<PresenterView>>){

    @Provides
    protected fun activity():BaseActivity<BasePresenter<PresenterView>>{
        return activity
    }

}
