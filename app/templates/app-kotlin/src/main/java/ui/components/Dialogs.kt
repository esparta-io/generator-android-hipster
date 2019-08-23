package <%= appPackage %>.ui.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.find
import <%= appPackage %>.R


@SuppressLint("InflateParams")
object Dialogs {
    
    @Suppress("ReturnCount")
    fun showProgressDialog(activity: AppCompatActivity, label: String): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.Dialogs)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null)
        val dialog = builder.create()
        dialog.setView(view)

        val txtLabel = view.find<TextView>(R.id.txt_progress_label)
        txtLabel.text = label

        // "overriding" backPressed()
        dialog.setOnKeyListener { _, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP && !keyEvent.isCanceled) {
                return@setOnKeyListener false
            }
            return@setOnKeyListener false
        }

        dialog.setCancelable(false)

        dialog.show()

        return dialog
    }
}