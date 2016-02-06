package <%= appPackage %>.ui.base;

import <%= appPackage %>.ui.base.BasePresenter;
import javax.inject.Inject;

public class EmptyPresenter extends BasePresenter<PresenterView> {

    @Inject
    public EmptyPresenter() { }

}
