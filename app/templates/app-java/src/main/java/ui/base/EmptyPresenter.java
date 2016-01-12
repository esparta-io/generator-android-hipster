package <%= appPackage %>.ui.base;

import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.ui.base.BasePresenter;
import javax.inject.Inject;

@ActivityScope
public class EmptyPresenter extends BasePresenter {

    @Inject
    public EmptyPresenter() {

    }

}
