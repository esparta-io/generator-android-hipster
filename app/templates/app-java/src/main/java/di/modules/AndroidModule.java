package <%= appPackage %>.di.modules;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import <%= appPackage %>.di.ForApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

@ForApplication
@Provides
public SharedPreferences provideSharedPreferences(Application application)  {
        return application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
}

@ForApplication
@Provides
public LayoutInflater provideLayoutInflater(Application application) {
        return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}

@ForApplication
@Provides
public InputMethodManager provideInputMethodManager(Application application) {
        return (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
}

@ForApplication
@Provides
public ActivityManager provideActivityManager(Application application)  {
        return (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
}
}
