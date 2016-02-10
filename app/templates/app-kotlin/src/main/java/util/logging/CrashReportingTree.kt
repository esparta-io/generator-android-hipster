package <%= appPackage %>.util.logging;

import android.util.Log;
import timber.log.Timber;

class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String, message: String, t: Throwable) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
    }
}
