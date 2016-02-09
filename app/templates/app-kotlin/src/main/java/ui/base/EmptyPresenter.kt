package <%= appPackage %>.ui.base

import <%= appPackage %>.ui.base.BasePresenter;
import javax.inject.Inject;

class EmptyPresenter @Inject constructor(): BasePresenter<EmptyView>() { }

interface EmptyView : PresenterView { }
