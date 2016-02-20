package <%= appPackage %>.ui.<%= activityPackageName %>

import android.os.Bundle;
import <%= appPackage %>.di.ActivityScope
import <%= appPackage %>.ui.base.BaseActivity
import <%= appPackage %>.di.HasComponent
import <%= appPackage %>.R
import <%= appPackage %>.application.App
<% if (componentType == 'createNew') { %>import <%= appPackage %>.di.components.Dagger<%= activityName %>Component
import <%= appPackage %>.di.components.<%= activityName %>Component
import <%= appPackage %>.di.modules.<%= activityName %>Module<% } else if (componentType == 'useApplication') { %>import <%= appPackage %>.di.components.ApplicationComponent<% } else {  %>import <%= appPackage %>.di.components.<%= UseExistingComponentName %>Component<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory <% } %>

import javax.inject.Inject

@ActivityScope
class <%= activityName %>Activity : BaseActivity<<%= activityName %>Presenter>(), <%= activityName %>View, HasComponent<<% if (componentType == 'createNew') { %><%= activityName %>Component<% } else if (componentType == 'useApplication') { %>ApplicationComponent<% } else {  %><%= useExistingComponentName %>Component<% } %>> {

    @Inject
    lateinit var <%= underscoreActivityName %>Presenter: <%= activityName %>Presenter

    lateinit var <% if (componentType == 'createNew') { %><%= underscoreActivityName %>Component: <%= activityName %>Component<% } else if (componentType == 'useApplication') { %>applicationComponent: ApplicationComponent<% } else {  %><%= underscoreUseExistingComponentName %>Component<% } %>

    override fun injectModule() {
        <% if (componentType == 'createNew') { %><%= underscoreActivityName %>Component = Dagger<%= activityName %>Component.builder().applicationComponent(App.get(this).getComponent()).<%= underscoreActivityName  %>Module(<%= activityName %>Module(this)).build()
        <%= underscoreActivityName %>Component.inject(this)
        <% } else if (componentType == 'useApplication') { %>applicationComponent = App.get(this).getComponent()
        applicationComponent.inject(this)<% } else {  %>
        <%= underscoreUseExistingComponentName %>Component = Dagger<%= useExistingComponentName %>Component.builder().applicationComponent(App.get(this).getComponent()).<%= underscoreUseExistingComponentName  %>Module(<%= useExistingComponentName %>Module(this)).build()
        <%= underscoreActivityName %>Component.inject(this)<% } %>
    }

    <% if (nucleus == true) { %>override fun getPresenterFactory(): PresenterFactory<<%= activityName %>Presenter>? = PresenterFactory { <%= underscoreActivityName  %>Presenter }<% } %>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_<%= underscoreActivityName  %>
    }

    override fun getComponent():  <% if (componentType == 'createNew') { %><%= activityName %>Component<% } else if (componentType == 'useApplication') { %>ApplicationComponent<% } else {  %><%= useExistingComponentName %>Component<% } %> {
        return  <% if (componentType == 'createNew') { %><%= underscoreActivityName %>Component<% } else if (componentType == 'useApplication') { %>applicationComponent<% } else {  %><%= underscoreUseExistingComponentName %>Component<% } %>
    }

}
