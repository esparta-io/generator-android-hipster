package <%= appPackage %>.application

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

import <%= appPackage %>.R
import <%= appPackage %>.di.ForApplication
import <%= appPackage %>.di.components.ApplicationComponent
import <%= appPackage %>.environment.EnvironmentConfiguration
import kotlin.properties.Delegates
import <%= appPackage %>.di.components.UserComponent
import <%= appPackage %>.di.modules.UserModule
import <%= appPackage %>.model.OAuth
import <%= appPackage %>.storage.Storage
<% if (threetenabp == true) { %>import com.jakewharton.threetenabp.AndroidThreeTen <% } %>
<% if (jodatime == true) { %>import net.danlew.android.joda.JodaTimeAndroid<% } %>
<% if (printview == true) { %>import com.github.johnkil.print.PrintConfig<% } %>
<% if (calligraphy == true) { %>import uk.co.chrisjenx.calligraphy.CalligraphyConfig<% } %>
<% if (glide == true) { %>import com.bumptech.glide.Glide<% } %>

import javax.inject.Inject


class App : Application() {

    var graph:ApplicationComponent? = null

    var userGraph: UserComponent? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @field:[Inject ForApplication]
    lateinit var context: Context

    @field:[Inject]
    lateinit var storage: Storage

    @Inject
    lateinit var environmentConfiguration: EnvironmentConfiguration

    override fun onCreate() {
        super.onCreate()

        <% if (jodatime === true) { %>JodaTimeAndroid.init(this)<% } %>
        <% if (threetenabp == true) { %>AndroidThreeTen.init(this) <% } %>
        <% if (printview === true) { %>PrintConfig.initDefault(assets, "fonts/MaterialIcons-Regular.ttf")<% } %>
        <% if (calligraphy === true) { %>CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build()) <% } %>

        graph = createComponent()
        environmentConfiguration.configure()
    }

    companion object {
        @JvmStatic fun get(context: Context): App {
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

    <% if (glide == true) { %>override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }<% } %>

    fun getUserComponent(): UserComponent? {
        if (graph == null) {
            createComponent()
        }
        if (userGraph == null) {
            val oauth = storage.get(OAuth.CLASS_NAME, OAuth::class.java)
            if (oauth != null) {
                createUserComponent(oauth)
            }
        }
        return userGraph
    }

    fun clearUserComponent() {
        userGraph = null
    }

    fun createUserComponent(oauth: OAuth): UserComponent {
        userGraph = getComponent().plus(UserModule(this, oauth))
        return userGraph!!
    }

}
