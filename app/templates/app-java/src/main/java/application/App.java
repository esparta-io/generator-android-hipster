package <%= appPackage %>.application;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

<% if (jodatime == true) { %>import net.danlew.android.joda.JodaTimeAndroid; <% } %>
<% if (printview == true) { %>import com.github.johnkil.print.PrintConfig; <% } %>

import <%= appPackage %>.environment.EnvironmentModule;
import <%= appPackage %>.environment.EnvironmentConfiguration;
import <%= appPackage %>.R;
import <%= appPackage %>.di.components.ApplicationComponent;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.di.components.DaggerApplicationComponent;
import <%= appPackage %>.di.modules.AndroidModule;
import <%= appPackage %>.di.modules.ApplicationModule;
import <%= appPackage %>.environment.EnvironmentConfiguration;
import <%= appPackage %>.util.gson.GsonModule;

import javax.inject.Inject;

<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyConfig; <% } %>

public class App extends Application {

public static ApplicationComponent graph;

    private static RefWatcher refWatcher;

    @ForApplication
    @Inject
    Context context;

    @Inject
    EnvironmentConfiguration environmentConfiguration;

    private static App instance = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
        instance = this;
        refWatcher = LeakCanary.install(this);

        <% if (jodatime == true) { %>JodaTimeAndroid.init(this); <% } %>
        <% if (printview == true) { %>PrintConfig.initDefault(getAssets(), "fonts/MaterialIcons-Regular.ttf");<% } %>
        <% if (calligraphy == true) { %>CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build()); <% } %>

        graph = createComponent();

        environmentConfiguration.configure();

    }

    public ApplicationComponent createComponent() {
        ApplicationComponent applicationComponent = ApplicationComponent.Initializer.init(this);

        applicationComponent.inject(this);
        return applicationComponent;
    }

    public static RefWatcher getRefWatcher() {
       return refWatcher;
    }


}
