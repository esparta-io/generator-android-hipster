package <%= appPackage %>.ui.main;

import android.os.Bundle;
import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.di.components.MainComponent;
import <%= appPackage %>.di.HasComponent;
import <%= appPackage %>.ui.base.BaseActivity;
import <%= appPackage %>.R;
import <%= appPackage %>.application.App;
import <%= appPackage %>.di.components.DaggerMainComponent;
import <%= appPackage %>.di.components.MainComponent;
import <%= appPackage %>.di.modules.MainModule;

import android.os.Bundle;

<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>

import javax.inject.Inject;

@ActivityScope
public class MainActivity extends BaseActivity<MainPresenter> implements MainView, HasComponent<MainComponent> {

        @Inject
        MainPresenter mainPresenter;

        MainComponent component;

        protected void injectModule() {
                component = DaggerMainComponent.builder().applicationComponent(App.graph).mainModule(new MainModule(this)).build();
                component.inject(this);
        }
          <% if (nucleus == true) { %>
        public PresenterFactory<MainPresenter> getPresenterFactory() {
                return () -> mainPresenter;
        }<% } %>

        public void onCreate(Bundle savedInstanceState ) {
                super.onCreate(savedInstanceState);
        }

        protected int getLayoutResource() {
                return R.layout.activity_main;
        }

        @Override
        public MainComponent getComponent() {
          return component;
        }

}
