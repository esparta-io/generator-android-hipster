package <%= appPackage %>.services.push;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

class DefaultPushReceiver : BroadcastReceiver() {

    fun onReceive(context: Context, intent: Intent) {
        val push = Intent(context, PushIntentService::class.java)
        push.putExtras(getResultExtras(true))
        context.startService(push)
        setResultCode(Activity.RESULT_OK)
    }
}