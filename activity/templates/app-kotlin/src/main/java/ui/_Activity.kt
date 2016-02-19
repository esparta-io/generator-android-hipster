package <%= appPackage %>.ui.<%= activityPackageName %>

import android.os.Bundle;
import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.di.HasComponent
import <%= appPackage %>.ui.base.BaseActivity
import <%= appPackage %>.di.HasComponent
import <%= appPackage %>.R
import <%= appPackage %>.application.App
import <%= appPackage %>.di.components.Dagger<%= activityName %>Component
import <%= appPackage %>.di.components.<%= activityName %>Component
import <%= appPackage %>.di.modules.<%= activityName %>Module

<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory <% } %>

import javax.inject.Inject

@ActivityScope
class <%= activityName %>Activity : BaseActivity<<%= activityName %>Presenter>(), <%= activityName %>View, HasComponent<<%= activityName %>Component> {

    @Inject
    lateinit var <%= underscoreActivityName  %>Presenter: <%= activityName %>Presenter

    lateinit var component: <%= activityName %>Component

    override fun injectModule() {
        component = Dagger<%= activityName %>Component.builder().applicationComponent(App.get(this).getComponent()).<%= underscoreActivityName  %>Module(<%= activityName %>Module(this)).build()
        component.inject(this)
    }

    <% if (nucleus == true) { %>override fun getPresenterFactory(): PresenterFactory<<%= activityName %>Presenter>? = PresenterFactory { <%= underscoreActivityName  %>Presenter }<% } %>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_<%= underscoreActivityName  %>
    }

    override fun getComponent(): <%= activityName %>Component {
        return component
    }

}
