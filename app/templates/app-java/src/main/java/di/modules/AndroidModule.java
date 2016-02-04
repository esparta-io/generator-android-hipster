package <%= appPackage %>.di.modules;

import <%= appPackage %>.di.ForApplication;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class AndroidModule {

    @Provides
    public SharedPreferences provideSharedPreferences(@ForApplication Context application) {
        return application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
    }

    @Provides
    public LayoutInflater provideLayoutInflater(@ForApplication Application application) {
        return (LayoutInflater) application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides
    public InputMethodManager provideInputMethodManager(@ForApplication Application application) {
        return (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides
    public NotificationManager provideNotificationManager(@ForApplication Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    public ActivityManager provideActivityManager(@ForApplication Application application) {
        return (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
    }
}
