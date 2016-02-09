package <%= appPackage %>.services.push;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class DefaultPushReceiver extends BroadcastReceiver {
    public DefaultPushReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent push = new Intent(context, PushIntentService.class);
        push.putExtras(getResultExtras(true));
        context.startService(push);
        setResultCode(Activity.RESULT_OK);
    }
}
