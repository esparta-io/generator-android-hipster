package <%= appPackage %>.extensions

import android.app.Activity
import android.content.Context
import android.content.BroadcastReceiver
import android.net.ConnectivityManager
import <%= appPackage %>.application.App
import <%= appPackage %>.di.components.UserComponent
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkCapabilities
import android.os.Build
import java.util.concurrent.atomic.AtomicInteger

fun Context.isConnected(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        when {
            capabilities == null -> false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION")
        manager.activeNetworkInfo?.isConnected ?: false
    }
}

fun Context.isWifiConnection(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        @Suppress("DEPRECATION")
        manager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
    } else {
        manager.getNetworkCapabilities(manager.activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }
}

fun Activity.makeLogin() {
    val loginIntent = Intent("TODO")
    loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    this.startActivity(loginIntent)
    this.finishAffinity()
}

fun Activity.getCurrentUserComponent(): UserComponent? {
    val component = App.get(this).getUserComponent()
    if (component == null) {
        makeLogin()
    }
    return component
}

val priority = AtomicInteger(1)

fun Context.registerSyncReceiver(receiver: BroadcastReceiver, vararg actions: String) {
    val filter = IntentFilter()
    actions.forEach {
        filter.addAction(it)
    }
    filter.priority = priority.incrementAndGet()
    registerReceiver(receiver, filter)
}
