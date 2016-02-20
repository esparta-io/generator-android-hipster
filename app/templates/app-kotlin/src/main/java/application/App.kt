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
<% if (glide == true) { %>import com.bumptech.glide.Glide<% } %>

import javax.inject.Inject


class App : Application() {

    var graph:ApplicationComponent? = null

    var refWatcher:RefWatcher? = null

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
        refWatcher = LeakCanary.install(this)

        <% if (jodatime === true) { %>JodaTimeAndroid.init(this)<% } %>
        <% if (printview === true) { %>PrintConfig.initDefault(assets, "fonts/MaterialIcons-Regular.ttf")<% } %>
        <% if (calligraphy === true) { %>CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build()) <% } %>

        graph = createComponent()

        environmentConfiguration.configure()

    }

    companion object {
        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }

    fun getComponent(): ApplicationComponent {
        if (graph == null) {
            createComponent()
        }
        return graph!!
    }

    private fun createComponent(): ApplicationComponent {
        val applicationComponent = ApplicationComponent.Initializer.init(this)
        applicationComponent.inject(this)
        return applicationComponent
    }

    fun recreateComponents() {
        graph = ApplicationComponent.Initializer.init(this)
        graph!!.inject(this)
        environmentConfiguration.configure()
    }

    fun refWatcher(): RefWatcher? {
        return refWatcher
    }

    <% if (glide == true) { %>override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }<% } %>

}
