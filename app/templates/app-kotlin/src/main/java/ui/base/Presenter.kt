package <%= appPackage %>.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This is a base class for all presenters. Subclasses can override
 * [.onCreate], [.onDestroy], [.onSave],
 * [.onTakeView], [.onDropView].
 *
 *
 * [Presenter.OnDestroyListener] can also be used by external classes
 * to be notified about the need of freeing resources.

 * @param  a type of view to return with [.getView].
 */
@Suppress("UNUSED_PARAMETER")
open class Presenter<View> {

    /**
     * Returns a current view attached to the presenter or null.

     * View is normally available between
     * [Activity.onResume] and [Activity.onPause],
     * [Fragment.onResume] and [Fragment.onPause],
     * [android.view.View.onAttachedToWindow] and [android.view.View.onDetachedFromWindow].

     * Calls outside of these ranges will return null.
     * Notice here that [Activity.onActivityResult] is called *before* [Activity.onResume]
     * so you can't use this method as a callback.

     * @return a current attached view.
     */
    @Nullable var view: View? = null
        private set

    private val onDestroyListeners = CopyOnWriteArrayList<OnDestroyListener>()

    /**
     * This method is called after presenter construction.

     * This method is intended for overriding.

     * @param savedState If the presenter is being re-instantiated after a process restart then this Bundle
     * *                   contains the data it supplied in [.onSave].
     */
    protected fun onCreate(@Nullable savedState: Bundle) {
    }

    /**
     * This method is being called when a user leaves view.

     * This method is intended for overriding.
     */
    protected fun onDestroy() {
    }

    /**
     * A returned state is the state that will be passed to [.onCreate] for a new presenter instance after a process restart.

     * This method is intended for overriding.

     * @param state a non-null bundle which should be used to put presenter's state into.
     */
    protected fun onSave(state: Bundle) {
    }

    /**
     * This method is being called when a view gets attached to it.
     * Normally this happens during [Activity.onResume], [android.app.Fragment.onResume]
     * and [android.view.View.onAttachedToWindow].

     * This method is intended for overriding.

     * @param view a view that should be taken
     */
    protected fun onTakeView(view: View) {
    }

    /**
     * This method is being called when a view gets detached from the presenter.
     * Normally this happens during [Activity.onPause] ()}, [Fragment.onPause] ()}
     * and [android.view.View.onDetachedFromWindow].

     * This method is intended for overriding.
     */
    protected fun onDropView() {
    }

    /**
     * A callback to be invoked when a presenter is about to be destroyed.
     */
    interface OnDestroyListener {
        /**
         * Called before [Presenter.onDestroy].
         */
        fun onDestroy()
    }

    /**
     * Adds a listener observing [.onDestroy].

     * @param listener a listener to add.
     */
    fun addOnDestroyListener(listener: OnDestroyListener) {
        onDestroyListeners.add(listener)
    }

    /**
     * Removed a listener observing [.onDestroy].

     * @param listener a listener to remove.
     */
    fun removeOnDestroyListener(listener: OnDestroyListener) {
        onDestroyListeners.remove(listener)
    }

    /**
     * Initializes the presenter.
     */
    fun create(bundle: Bundle) {
        onCreate(bundle)
    }

    /**
     * Destroys the presenter, calling all [Presenter.OnDestroyListener] callbacks.
     */
    fun destroy() {
        for (listener in onDestroyListeners)
            listener.onDestroy()
        onDestroy()
    }

    /**
     * Saves the presenter.
     */
    fun save(state: Bundle) {
        onSave(state)
    }

    /**
     * Attaches a view to the presenter.

     * @param view a view to attach.
     */
    fun takeView(view: View) {
        this.view = view
        onTakeView(view)
    }

    /**
     * Detaches the presenter from a view.
     */
    <% if (nucleus == false) { %>open<% } %> fun dropView() {
        onDropView()
        this.view = null
    }
}
