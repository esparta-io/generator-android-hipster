package <%= appPackage %>.service

import android.content.Context
import android.content.Intent
import <%= appPackage %>.model.OAuth
import <%= appPackage %>.service.push.PushExtras
import <%= appPackage %>.storage.Storage

object LogoutWorker {

    fun logoutAndStopDownloads(context: Context, storage: Storage) {
        doLogout(context, storage)
    }

    fun logoutAndStopDownloadsDueToInvalidToken(context: Context, storage: Storage) {
        doLogout(context, storage, false)
    }

    private fun doLogout(context: Context, storage: Storage, clearDatabase: Boolean = true) {
        if (clearDatabase) {
            val auth = storage.get(OAuth::class.java)
        }

        val username = storage.getString(OAuth.USER_NAME)
        storage.clearAll()
        storage.setString(OAuth.USER_NAME, username)
        context.sendBroadcast(Intent(PushExtras.UNAUTHORIZED))
    }
}
