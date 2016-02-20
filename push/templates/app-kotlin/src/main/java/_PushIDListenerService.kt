package <%= appPackage %>.services.push;

import android.content.Intent
import com.google.android.gms.iid.InstanceIDListenerService

class PushIDListenerService : InstanceIDListenerService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        // TODO update your device token here
    }

}
