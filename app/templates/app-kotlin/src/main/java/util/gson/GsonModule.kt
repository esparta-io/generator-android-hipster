package <%= appPackage %>.util.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder

<% if (jodamoney == true) { %>import org.joda.money.CurrencyUnit
import org.joda.money.Money<% } %>
<% if (jodatime == true) { %>import org.joda.time.DateTime
import org.joda.time.DateTimeZone<% } %>

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class GsonModule {

    @Provides
    @Singleton
    fun provideDefaultGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        <% if (jodamoney == true) { %>gsonBuilder.registerTypeAdapter(Money::class.java, MoneyTypeConverter())
        gsonBuilder.registerTypeAdapter(CurrencyUnit::class.java, CurrencyUnitTypeConverter()) <% } %>
        <% if (jodatime == true) { %>gsonBuilder.registerTypeAdapter(DateTime::class.java, DateTimeTypeConverter())
        gsonBuilder.registerTypeAdapter(DateTimeZone::class.java, DateTimeZoneTypeConverter()) <% } %>
        return gsonBuilder
    }

    @Provides
    @Singleton
    fun provideGson(gsonBuilder: GsonBuilder): Gson {
        return gsonBuilder.create()
    }

}
