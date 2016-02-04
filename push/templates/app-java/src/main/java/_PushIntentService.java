package <%= appPackage %>.service.push;

import  <%= appPackage %>.application.App;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

public class PushIntentService extends IntentService {

    public PushIntentService() {
        super("PushIntentService");
    }

    @Inject
    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        App.graph.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handleNotification(intent.getExtras());
    }

    private void handleNotification(Bundle extras) {

      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(PushIntentService.this)
              .setSmallIcon(R.mipmap.ic_launcher)
              .setContentTitle("Title")
              .setContentText("Text")
              .setContentIntent(contentIntent);

      mBuilder.setStyle(new NotificationCompat.InboxStyle()
              .setBigContentTitle("Big Content")
              .addLine("Line 1");


      notificationManager.notify(1, mBuilder.build());

    }

}
