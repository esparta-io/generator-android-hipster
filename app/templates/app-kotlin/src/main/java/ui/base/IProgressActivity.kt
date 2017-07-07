package <%= appPackage %>.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.StringRes
import org.jetbrains.anko.indeterminateProgressDialog

interface IProgressActivity {

    var progress: ProgressDialog?

    fun hideProgressLoading() {
        progress?.dismiss()
        progress = null
    }

    fun showProgressLoading(context: Context, progressMessage: String?, progressTitle: String? = "") {
        progress?.dismiss()
        progress = context.indeterminateProgressDialog(message = progressMessage ?: "Processando", title = progressTitle)
        progress?.show()
    }

    fun showProgressLoading(context: Context, @StringRes progressMessage: Int, progressTitle: String? = "") {
        progress?.dismiss()
        progress = context.indeterminateProgressDialog(message = context.getString(progressMessage), title = progressTitle)
        progress?.show()
    }
}
