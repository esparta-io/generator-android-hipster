package <%= appPackage %>.ui.base

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import <%= appPackage %>.extensions.snackbar

interface IFailMessage {

    fun defaultFailMessage(activity: AppCompatActivity, view: View, e: Throwable, @StringRes genericFriendlyMessage: Int) {
        doShowMessage(activity, view, e, activity.getString(genericFriendlyMessage))
    }

    fun defaultFailMessage(activity: AppCompatActivity, view: View, e: Throwable, friendlyMessage: String) {
        doShowMessage(activity, view, e, friendlyMessage)
    }

    private fun doShowMessage(activity: AppCompatActivity, view: View, e: Throwable, friendlyMessage: String) {
        val suffix = if (e.message != null) e.message else ""
        activity.snackbar(view, friendlyMessage + " $suffix")
    }
}
