package <%=appPackage%>.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import <%=appPackage%>.R;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static boolean hasAcceptedAll(@NonNull int... permissions) {
        for (int permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != permission) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getPermissionsRationaleThatWasNotAllowed(Activity activity, String[] permissions) {
        List<String> rationales = new ArrayList<>();
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                rationales.add(permission);
            }
        }
        return rationales;
    }

    public static boolean hasAcceptedAllPermissions(Activity activity, @NonNull String... permissions) {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getPendingPermissions(Activity activity, String[] permissions) {
        List<String> pending = new ArrayList<>();
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, permission)) {
                pending.add(permission);
            }
        }
        return pending;
    }


    public static void snackbarForSettings(Activity activity, int REQUEST_SETTINGS) {
        Snackbar make = Snackbar.make(activity.findViewById(android.R.id.content), "R.string.need_permissions", Snackbar.LENGTH_INDEFINITE);
        make.setAction("R.string.settings", v -> {
            make.dismiss();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
            intent.setFlags(0);
            activity.startActivityForResult(intent, REQUEST_SETTINGS);
        });
        make.show();
    }

    public static void showDialogRationale(Activity activity, PermissionRationaleCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("R.string.permissions_required");
        builder.setMessage("R.string.permissions_required_to_use_app");
        builder.setCancelable(false);
        builder.setPositiveButton("R.string.ok", (dialog, which) -> {
            dialog.dismiss();
            callback.onAccept();
        });
        builder.show();
    }

    public interface PermissionRationaleCallback {
        void onAccept();
    }
}
