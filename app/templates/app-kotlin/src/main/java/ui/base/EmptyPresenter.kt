package <%= appPackage %>.ui.base

import <%= appPackage %>.di.ActivityScope;
import <%= appPackage %>.ui.base.BasePresenter;
import javax.inject.Inject;

@ActivityScope
class EmptyPresenter @Inject constructor(): BasePresenter<EmptyView>() { }

interface EmptyView : PresenterView { }
