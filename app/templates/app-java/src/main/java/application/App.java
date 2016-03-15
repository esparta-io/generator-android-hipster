package <%= appPackage %>.application;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

<% if (jodatime == true) { %>import net.danlew.android.joda.JodaTimeAndroid; <% } %>
<% if (threetenabp == true) { %>import com.jakewharton.threetenabp.AndroidThreeTen; <% } %>
<% if (printview == true) { %>import com.github.johnkil.print.PrintConfig; <% } %>
<% if (glide == true) { %>import com.bumptech.glide.Glide;<% } %>

import <%= appPackage %>.environment.EnvironmentConfiguration;
import <%= appPackage %>.R;
import <%= appPackage %>.di.components.ApplicationComponent;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.di.components.DaggerApplicationComponent;

import javax.inject.Inject;

<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyConfig; <% } %>

public class App extends Application {

    private ApplicationComponent graph;

    private RefWatcher refWatcher;

    @ForApplication
    @Inject
    Context context;

    @Inject
    EnvironmentConfiguration environmentConfiguration;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
        refWatcher = LeakCanary.install(this);

        <% if (jodatime == true) { %>JodaTimeAndroid.init(this); <% } %>
        <% if (threetenabp == true) { %>  AndroidThreeTen.init(this); <% } %>
        <% if (printview == true) { %>PrintConfig.initDefault(getAssets(), "fonts/MaterialIcons-Regular.ttf");<% } %>
        <% if (calligraphy == true) { %>CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build()); <% } %>

        graph = createComponent();

        environmentConfiguration.configure();

    }

    public RefWatcher getRefWatcher() {
        if (refWatcher == null) {
            refWatcher = LeakCanary.install(this);
        }
        return refWatcher;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (graph == null) {
            createComponent();
        }
        return graph;
    }

    private ApplicationComponent createComponent() {
        graph = ApplicationComponent.Initializer.init(this);
        graph.inject(this);
        return graph;
    }

    <% if (glide == true) { %>@Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }<% } %>

    public void recreateComponents() {
        graph = ApplicationComponent.Initializer.init(this);
        graph.inject(this);
        environmentConfiguration.configure();
    }


}
