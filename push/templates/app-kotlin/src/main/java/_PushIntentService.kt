package <%= appPackage %>.services.push;

import  <%= appPackage %>.R;
import  <%= appPackage %>.application.App;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

class PushIntentService : IntentService("PushIntentService") {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        App.get(this).getComponent.inject(this)
    }

    override fun onHandleIntent(intent: Intent) {
        handleNotification(intent.extras)
    }

    private fun handleNotification(extras: Bundle) {

        val mBuilder = NotificationCompat.Builder(this@PushIntentService).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title").setContentText("Text")

        mBuilder.setStyle(NotificationCompat.InboxStyle().setBigContentTitle("Big Content").addLine("Line 1"))

        notificationManager.notify(1, mBuilder.build())

    }

}
