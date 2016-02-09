package <%= appPackage %>.application

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.github.johnkil.print.PrintConfig
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import io.github.cavarzan.R
import io.github.cavarzan.di.ForApplication
import io.github.cavarzan.di.components.ApplicationComponent
import net.danlew.android.joda.JodaTimeAndroid
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

import <%= appPackage %>.R
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.di.components.ApplicationComponent
import io.github.cavarzan.environment.EnvironmentConfiguration

<% if (jodatime == true) { %>import net.danlew.android.joda.JodaTimeAndroid<% } %>
<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyConfig<% } %>
import javax.inject.Inject


class App : Application() {

    companion object {

        var graph by Delegates.notNull<ApplicationComponent>();

        var refWatcher: RefWatcher? = null
            private set

        private var instance: App? = null
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @field:[Inject ForApplication]
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()

        LeakCanary.install(this)
        instance = this
        refWatcher = LeakCanary.install(this)

        <% if (jodatime === true) { %>JodaTimeAndroid.init(this)<% } %>
        <% if (printview === true) { %>PrintConfig.initDefault(getAssets(), "fonts/MaterialIcons-Regular.ttf")<% } %>
        <% if (calligraphy === true) { %>CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build()) <% } %>

        graph = createComponent()

        environmentConfiguration.configure()

    }

    fun createComponent(): ApplicationComponent {
        val applicationComponent = ApplicationComponent.Initializer.init(this)

        applicationComponent.inject(this)
        return applicationComponent
    }

    fun getRefWatcher(): RefWatcher {
        return refWatcher
    }

}
