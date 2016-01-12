package <%= appPackage %>.ui.main;

import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.ui.base.BasePresenter;

import javax.inject.Inject;

@ActivityScope
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

}
