package <%= appPackage %>.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isConnected(): Boolean {
  val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  return manager.activeNetworkInfo?.isConnectedOrConnecting ?: true
}
