package <%= appPackage %>.environment;

import <%= appPackage %>.application.App;
import <%= appPackage %>.di.ForApplication;
<% if (mixpanel == true) { %>import com.mixpanel.android.mpmetrics.MixpanelAPI;<% } %>
import dagger.Module
import dagger.Provides
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EnvironmentModule(val app: App) {

    @Provides
    @Singleton
    fun provideRestAdapter(): Retrofit  {
        val restAdapter = Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create()).build()
        return restAdapter
    }

    <% if (mixpanel == true) { %>@Provides
    @Singleton
    fun provideMixpanelApi(@ForApplication application: App): MixpanelAPI{
        val mixpanel = MixpanelAPI.getInstance(application,"token")
        return mixpanel
    }<% } %>

}
