package <%= appPackage %>.ui.base

<% if (nucleus == true) { %>import nucleus.presenter.RxPresenter; <% } %>

<% if (nucleus == false) { %>import io.reactivex.disposables.Disposable
import java.util.* <% } %>

<% if (timber == true) { %>import timber.log.Timber <% } %>

<% if (nucleus == true) { %>abstract class BasePresenter<V : PresenterView> : Rx } Presenter<V>()<% } %>

<% if (nucleus == false) { %>abstract class BasePresenter<V : PresenterView> : Presenter<V>() {
      private var disposableList = ArrayList<Disposable>()

      fun add(disposable: Disposable) {
          disposableList .add(disposable)
      }

      fun unSubscribe() {
          disposableList
                  .filter { ! it.isDisposed }
                  .forEach { it.dispose() }
      }

      override fun dropView() {
          super.dropView()
          unSubscribe()
      }
}
<% } %>
