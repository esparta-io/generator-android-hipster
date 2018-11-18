package <%= appPackage %>.ui.base

import javax.inject.Inject;

@Suppress("EmptyClassBlock")
class EmptyPresenter @Inject constructor(): BasePresenter<EmptyView>() { }

@Suppress("EmptyClassBlock")
interface EmptyView : PresenterView { }
