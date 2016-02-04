package <%= appPackage %>.ui.<%= activityPackageName %>;

import android.os.Bundle;
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
<% } %>

<% if (nucleus == true) { %>import nucleus.factory.PresenterFactory; <% } %>

import javax.inject.Inject;

public class <%= interactorName %>Interactor extends BaseInteractor {

        @Inject
        public <%= interactorName %>Interactor(ThreadExecutor executor) {
            super(executor);
        }

        public void invoke() {
            // TODO 
        }

}
