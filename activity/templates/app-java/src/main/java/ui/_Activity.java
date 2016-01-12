package <%= appPackage %>.ui.<%= activityPackageName %>;

import android.os.Bundle;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.HasComponent;
import <%= appPackage %>.ui.base.BaseActivity;
import <%= appPackage %>.R;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.Dagger<%= activityName %>Component;
import <%= appPackage %>.di.components.<%= activityName %>Component;
import <%= appPackage %>.di.modules.<%= activityName %>Module;

<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>

import javax.inject.Inject;

@ActivityScope
public class <%= activityName %>Activity extends BaseActivity<<%= activityName %>Presenter> implements <%= activityName %>View, HasComponent<<%= activityName %>Component> {

        @Inject
        <%= activityName %>Presenter <%= activityName.toLowerCase() %>Presenter;

        <%= activityName %>Component component;

        protected void injectModule() {
                component = Dagger<%= activityName %>Component.builder().applicationComponent(App.graph).<%= activityName.toLowerCase() %>Module(new <%= activityName %>Module(this)).build();
                component.inject(this);
        }
          <% if (nucleus == true) { %>
        public PresenterFactory<<%= activityName %>Presenter> getPresenterFactory() {
                return () -> <%= activityName.toLowerCase() %>Presenter;
        }<% } %>

        public void onCreate(Bundle savedInstanceState ) {
                super.onCreate(savedInstanceState);
        }

        protected int getLayoutResource() {
                return R.layout.activity_<%= activityName.toLowerCase() %>;
        }

        @Override
        public <%= activityName %>Component getComponent() {
          return component;
        }

}
