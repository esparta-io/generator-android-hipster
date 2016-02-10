package <%= appPackage %>.environment;

import <%= appPackage %>.application.App;

import javax.inject.Singleton;
import <%= appPackage %>.di.ForApplication;
import <%= appPackage %>.application.App;
<% if (mixpanel == true) { %>import com.mixpanel.android.mpmetrics.MixpanelAPI;<% } %>

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Module
class EnvironmentModule(val app: App) {

    @Provides
    @Singleton
    fun provideRestAdapter() : Retrofit  {
        val restAdapter = Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create()).build()
        return restAdapter
    }

    <% if (mixpanel == true) { %>@Provides
    @Singleton
    fun provideMixpanelApi(@ForApplication application: App): MixpanelAPI{
        val mixpanel=MixpanelAPI.getInstance(application,"token")
        return mixpanel
    }

    public MixpanelAPI provideMixpanelApi(@ForApplication App application) {
    	   MixpanelAPI mixpanel = MixpanelAPI.getInstance(application, "token");
    	   return mixpanel;
    }<% } %>

}
