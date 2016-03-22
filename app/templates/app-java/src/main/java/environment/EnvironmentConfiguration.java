package <%= appPackage %>.environment;

import android.app.Application;
import android.os.StrictMode;

import <%= appPackage %>.BuildConfig;
import <%= appPackage %>.di.ForApplication;

import javax.inject.Inject;
import javax.inject.Singleton;
import rx.schedulers.Schedulers;

<% if (timber == true) { %>import timber.log.Timber;
import <%= appPackage %>.util.logging.CrashReportingTree; <% } %>
<% if (stetho == true) { %>import com.facebook.stetho.Stetho;<% } %>

@Singleton
public class EnvironmentConfiguration {

    @Inject
    @ForApplication
    Application app;

    @Inject
    public EnvironmentConfiguration() {
    }

    public void configure() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        <% if (stetho == true) { %>Schedulers.io().createWorker().schedule(() -> Stetho.initializeWithDefaults(app));<% } %>
        <% if (timber == true) { %>if (BuildConfig.DEBUG) {
           Timber.plant(new Timber.DebugTree());
        } else {
           Timber.plant(new CrashReportingTree());
        }<% } %>
    }

}
