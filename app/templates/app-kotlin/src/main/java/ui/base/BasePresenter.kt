package <%= appPackage %>.ui.base

<% if (nucleus == true) { %>import nucleus.presenter.RxPresenter; <% } %>

abstract class BasePresenter<V : PresenterView> : <% if (nucleus == true) { %>Rx<% } %>Presenter<V>()
