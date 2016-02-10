package <%= appPackage %>.environment;

import android.app.Application;
import android.os.StrictMode;

import <%= appPackage %>.BuildConfig;
import <%= appPackage %>.di.ForApplication;

import javax.inject.Inject;
import javax.inject.Singleton;

<% if (timber == true) { %>import timber.log.Timber;
import <%= appPackage %>.util.logging.CrashReportingTree; <% } %>
<% if (stetho == true) { %>import com.facebook.stetho.Stetho;<% } %>

@Singleton
class EnvironmentConfiguration @Inject constructor() {

    @Inject
    @ForApplication
    lateinit var app: Application

    fun configure() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        <% if (stetho == true) { %>Stetho.initializeWithDefaults(app)<% } %>
        <% if (timber == true) { %>if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }<% } %>
    }

}
