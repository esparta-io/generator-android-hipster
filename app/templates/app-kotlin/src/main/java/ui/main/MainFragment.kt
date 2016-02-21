package <%= appPackage %>.ui.main

import <%= appPackage %>.di.components.MainComponent
import <%= appPackage %>.ui.base.BaseFragment
import <%= appPackage %>.ui.base.EmptyPresenter
import <%= appPackage %>.R


class MainFragment : BaseFragment<EmptyPresenter>() {

    override fun inject() {
        getComponent(MainComponent::class.java).inject(this)
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_main
    }

    <% if (nucleus == false) { %>override fun getPresenter(): EmptyPresenter {
        return EmptyPresenter()
    }<% } %>
}
