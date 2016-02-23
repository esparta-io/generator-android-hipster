package <%= appPackage %>.ui.<%= activityPackageName %>;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Pair;

import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.HasComponent;
import <%= appPackage %>.ui.base.BaseActivity;
import <%= appPackage %>.R;
import <%= appPackage %>.application.App;
<% if (componentType == 'createNew') { %>
import <%= appPackage %>.di.components.Dagger<%= activityName %>Component;
import <%= appPackage %>.di.components.<%= activityName %>Component;
import <%= appPackage %>.di.modules.<%= activityName %>Module;
<% } else if (componentType == 'useApplication') { %>
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.ApplicationComponent;
<% } else {  %>import <%= appPackage %>.di.components.<%= useExistingComponentName %>Component;<% } %>
<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>

import javax.inject.Inject;

<% if (componentType == 'createNew') { %>@ActivityScope<% } %>
public class <%= activityName %>Activity extends BaseActivity<<%= activityName %>Presenter> implements <%= activityName %>View, HasComponent<<% if (componentType == 'createNew') { %><%= activityName %>Component<% } else if (componentType == 'useApplication') { %>ApplicationComponent<% } else {  %><%= useExistingComponentName %>Component<% } %>> {

    @Inject
    <%= activityName %>Presenter <%= activityName.charAt(0).toLowerCase()+activityName.slice(1) %>Presenter;

    <% if (componentType == 'createNew') { %><%= activityName %>Component <%= activityName.charAt(0).toLowerCase() + activityName.slice(1) %>Component;<% } else if (componentType == 'useApplication') { %>ApplicationComponent applicationComponent;<% } else {  %> <%= useExistingComponentName %>Component <%= underscoreUseExistingComponentName %>Component;<% } %>

    protected void injectModule() {
        <% if (componentType == 'createNew') { %><%= activityName.charAt(0).toLowerCase() + activityName.slice(1) %>Component = Dagger<%= activityName %>Component.builder().applicationComponent(App.get(this).getComponent()).<%= activityName.charAt(0).toLowerCase() + activityName.slice(1)  %>Module(new <%= activityName %>Module(this)).build();
        <%= activityName.charAt(0).toLowerCase() + activityName.slice(1) %>Component.inject(this);
        <% } else if (componentType == 'useApplication') { %>applicationComponent = App.get(this).getComponent();
        applicationComponent.inject(this);<% } else if (useExistingComponentNameApplication == false) {  %>
        <%= underscoreUseExistingComponentName %>Component = Dagger<%= useExistingComponentName %>Component.builder().applicationComponent(App.get(this).getComponent()).<%= underscoreUseExistingComponentName  %>Module(new <%= useExistingComponentName %>Module(this)).build();
        <%= activityName.charAt(0).toLowerCase() + activityName.slice(1) %>Component.inject(this);<% } else { %><%= underscoreUseExistingComponentName %>Component = App.get(this).get<% if (useExistingComponentName != 'Application') { %><%= underscoreUseExistingComponentName %><% } %>Component();
        <%= underscoreUseExistingComponentName %>Component.inject(this);
        <% } %>
    }
    <% if (nucleus == true) { %>
    public PresenterFactory<<%= activityName %>Presenter> getPresenterFactory() {
        return () -> <%= activityName.charAt(0).toLowerCase()+activityName.slice(1) %>Presenter;
    }<% } %>

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected int getLayoutResource() {
        return R.layout.activity_<%= underscoreActivityName %>;
    }

    @Override
    public <% if (componentType == 'createNew') { %><%= activityName %>Component<% } else if (componentType == 'useApplication') { %>ApplicationComponent<% } else {  %><%= useExistingComponentName %>Component<% } %> getComponent() {
        return <% if (componentType == 'createNew') { %><%= activityName.charAt(0).toLowerCase() + activityName.slice(1) %>Component;<% } else if (componentType == 'useApplication') { %>applicationComponent;<% } else {  %><%= underscoreUseExistingComponentName %>Component;<% } %>
    }

    <% if (nucleus == false) { %>
    @Override
    protected <%= activityName %>Presenter getPresenter() {
        return <%= activityName.charAt(0).toLowerCase()+activityName.slice(1) %>Presenter;
    }<% } %>

}
