package <%= appPackage %>.ui.<%= activityPackageName %>;

import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.ui.base.BasePresenter;

import javax.inject.Inject;

@ActivityScope
public class <%= activityName %>Presenter extends BasePresenter<<%= activityName %>View> {

    @Inject
    public <%= activityName %>Presenter() {

    }

}
