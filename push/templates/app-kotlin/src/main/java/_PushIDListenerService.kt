package <%= appPackage %>.services.push;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class PushIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // TODO update your device token here
    }

}
