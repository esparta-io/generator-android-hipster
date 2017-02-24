package <%= appPackage %>.ui.base

<% if (nucleus == true) { %>import nucleus.presenter.RxPresenter; <% } %>

<% if (nucleus == false) { %>import rx.Subscription
import java.util.* <% } %>

<% if (timber == true) { %>import timber.log.Timber <% } %>

<% if (nucleus == true) { %>abstract class BasePresenter<V : PresenterView> : Rx } Presenter<V>()<% } %>

<% if (nucleus == false) { %>abstract class BasePresenter<V : PresenterView> : Presenter<V>() {
    private var subscriptionList = ArrayList<Subscription>()

    fun add(subscription: Subscription) {
        subscriptionList.add(subscription)
    }

    fun unSubscribe() {
        subscriptionList
                .filter { it.isUnsubscribed }
                .forEach {
                    try {
                        it.unsubscribe()
                    } catch (e: Throwable) { <% if (timber == true) { %>Timber.e(e, "unSubscribe()")<% } %> <% if (timber == false) { %>e.printStackTrace()<% } %> }
                }
    }

    override fun dropView() {
        super.dropView()
        unSubscribe()
    }
}
<% } %>