package <%= appPackage %>.services.push;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import <%= appPackage %>.application.App;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import javax.inject.Inject;

class PushServiceListener : GcmListenerService() {

    override fun onCreate() {
        super.onCreate()
        App.get(this).getComponent().inject(this)
    }

    override fun onMessageReceived(from: String, data: Bundle) {
        val filter = IntentFilter()
        filter.addAction("Events.PUSH")
        val broadcast = Intent()
        broadcast.putExtras(data)
        broadcast.action = "Events.PUSH"
        sendOrderedBroadcast(broadcast, null, null, null, Activity.RESULT_OK, null, data)
    }
}

