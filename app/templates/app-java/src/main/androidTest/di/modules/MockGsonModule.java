package <%= appPackage %>.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import <%= appPackage %>.util.gson.AutoValueTypeAdapterFactory;
import <%= appPackage %>.util.gson.CurrencyUnitTypeConverter;
import <%= appPackage %>.util.gson.MoneyTypeConverter;

import org.aaronhe.threetengson.ThreeTenGsonAdapter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by deividi on 23/03/16.
 */
@Module
public class MockGsonModule  {

    @Provides
    @Singleton
    public GsonBuilder provideDefaultGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Money.class, new MoneyTypeConverter());
        gsonBuilder.registerTypeAdapter(CurrencyUnit.class, new CurrencyUnitTypeConverter());
        gsonBuilder.registerTypeAdapterFactory(new AutoValueTypeAdapterFactory());

        ThreeTenGsonAdapter.registerAll(gsonBuilder);

        return gsonBuilder;
    }

    @Provides
    @Singleton
    public Gson provideGson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }

}

