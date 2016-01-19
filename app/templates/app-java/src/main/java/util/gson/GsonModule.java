package <%= appPackage %>.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

<% if (jodamoney == true) { %>import org.joda.money.CurrencyUnit;
import org.joda.money.Money;<% } %>
<% if (jodatime == true) { %>import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;<% } %>

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

  @Provides
  @Singleton
  public GsonBuilder provideDefaultGsonBuilder() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    <% if (jodamoney == true) { %>gsonBuilder.registerTypeAdapter(Money.class, new MoneyTypeConverter());
      gsonBuilder.registerTypeAdapter(CurrencyUnit.class, new CurrencyUnitTypeConverter()); <% } %>
    <% if (jodamoney == true) { %>gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
        gsonBuilder.registerTypeAdapter(DateTimeZone.class, new DateTimeZoneTypeConverter());<% } %>

    return gsonBuilder;
  }

  @Provides
  @Singleton
  Gson provideGson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }

}
