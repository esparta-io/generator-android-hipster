package <%= appPackage %>.ui.base

import android.app.Dialog
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import <%= appPackage %>.ui.components.Dialogs

interface IProgressActivity {

    var progress: Dialog?

    fun hideProgressLoading() {
        progress?.dismiss()
        progress = null
    }

    fun showProgressLoading(activity: AppCompatActivity, progressTitle: String) {
        progress?.dismiss()
        progress = Dialogs.showProgressDialog(activity, progressTitle)
        progress?.show()
    }

    fun showProgressLoading(activity: AppCompatActivity, @StringRes progressTitle: Int) {
        progress?.dismiss()
        Dialogs.showProgressDialog(activity, activity.getString(progressTitle))

        progress?.show()
    }
}
