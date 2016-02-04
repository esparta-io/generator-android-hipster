package <%= appPackage %>.ui.<%= activityPackageName %>

import <%= appPackage %>.di.components.<%= activityName %>Component
import <%= appPackage %>.ui.base.BaseFragment
import <%= appPackage %>.ui.base.EmptyPresenter
import <%= appPackage %>.R


class <%= activityName %>Fragment : BaseFragment<EmptyPresenter>() {

    override protected fun inject() {
        getComponent(<%= activityName %>Component::class.java).inject(this)
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_<%= activityName.toLowerCase() %>
    }

}
