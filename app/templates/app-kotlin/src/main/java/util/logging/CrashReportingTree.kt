package <%= appPackage %>.util.logging;

import android.util.Log;
import timber.log.Timber;

public class CrashReportingTree : Timber.Tree() {

  protected override fun log(priority: Int, tag: String, message: String, t: Throwable) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
      return
    }
  }
}
