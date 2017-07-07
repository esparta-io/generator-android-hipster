package <%= appPackage %>.environment

import android.app.Application
import android.os.StrictMode
import <%= appPackage %>.BuildConfig
import <%= appPackage %>.di.ForApplication
<% if (stetho == true) { %>import com.facebook.stetho.Stetho;<% } %>
<% if (timber == true) { %>import timber.log.Timber;
import <%= appPackage %>.util.logging.CrashReportingTree; <% } %>
import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.schedulers.Schedulers

@Singleton
class EnvironmentConfiguration @Inject constructor() {

    @field:[Inject ForApplication]
    lateinit var app: Application

    fun configure() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        <% if (stetho == true) { %>Schedulers.io().createWorker().schedule { Stetho.initializeWithDefaults(app) }<% } %>
        <% if (timber == true) { %>if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }<% } %>
    }

}
