package <%= appPackage %>.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog

import <%= appPackage %>.R

import java.util.ArrayList

object PermissionUtils {

    fun hasAcceptedAll(vararg permissions: Int): Boolean {
        for (permission in permissions) {
            if (PackageManager.PERMISSION_GRANTED != permission) {
                return false
            }
        }
        return true
    }

    fun getPermissionsRationaleThatWasNotAllowed(activity: Activity, permissions: Array<String>): List<String> {
        val rationales = ArrayList<String>()
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                rationales.add(permission)
            }
        }
        return rationales
    }

    fun hasAcceptedAllPermissions(activity: Activity, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, permission)) {
                return false
            }
        }
        return true
    }

    fun getPendingPermissions(activity: Activity, permissions: Array<String>): List<String> {
        val pending = ArrayList<String>()
        for (permission in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, permission)) {
                pending.add(permission)
            }
        }
        return pending
    }


    fun snackbarForSettings(activity: Activity, requestSettings: Int) {
        val make = Snackbar.make(activity.findViewById(android.R.id.content), "R.string.need_permissions", Snackbar.LENGTH_INDEFINITE)
        make.setAction("R.string.settings") { _ ->
            make.dismiss()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.packageName))
            intent.flags = 0
            activity.startActivityForResult(intent, requestSettings)
        }
        make.show()
    }

    fun showDialogRationale(activity: Activity, callback: PermissionRationaleCallback) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("R.string.permissions_required")
        builder.setMessage("R.string.permissions_required_to_use_app")
        builder.setCancelable(false)
        builder.setPositiveButton("R.string.ok") { dialog, _ ->
            dialog.dismiss()
            callback.onAccept()
        }
        builder.show()
    }

    interface PermissionRationaleCallback {
        fun onAccept()
    }
}
