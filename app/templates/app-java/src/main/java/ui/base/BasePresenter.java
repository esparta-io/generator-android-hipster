package <%= appPackage %>.ui.base;

<% if (nucleus == true) { %>import nucleus.presenter.RxPresenter; <% } %>

public abstract class BasePresenter<V extends PresenterView> extends <% if (nucleus == true) { %>RxPresenter<% } else { %>Presenter<% } %> <V> {

}
