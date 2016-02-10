package <%= appPackage %>.application

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

import <%= appPackage %>.R
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.di.components.ApplicationComponent
import <%= appPackage %>.environment.EnvironmentConfiguration
import kotlin.properties.Delegates

<% if (jodatime == true) { %>import net.danlew.android.joda.JodaTimeAndroid<% } %>
<% if (printview == true) { %>import com.github.johnkil.print.PrintConfig<% } %>
<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyConfig<% } %>
import javax.inject.Inject


class App : Application() {

    companion object {

        var graph by Delegates.notNull<ApplicationComponent>();
        var refWatcher by Delegates.notNull<RefWatcher>();
        var instance by Delegates.notNull<App>();

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @field:[Inject ForApplication]
    lateinit var context: Context

    @Inject
    lateinit var environmentConfiguration: EnvironmentConfiguration

    override fun onCreate() {
        super.onCreate()

        LeakCanary.install(this)
        instance = this
        refWatcher = LeakCanary.install(this)

        <% if (jodatime === true) { %>JodaTimeAndroid.init(this)<% } %>
        <% if (printview === true) { %>PrintConfig.initDefault(assets, "fonts/MaterialIcons-Regular.ttf")<% } %>
        <% if (calligraphy === true) { %>CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build()) <% } %>

        graph = createComponent()

        environmentConfiguration.configure()

    }

    fun createComponent(): ApplicationComponent {
        val applicationComponent = ApplicationComponent.Initializer.init(this)

        applicationComponent.inject(this)
        return applicationComponent
    }

    fun getRefWatcher(): RefWatcher? {
        return refWatcher
    }

}
