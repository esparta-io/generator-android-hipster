package <%= appPackage %>.di.modules

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import dagger.Module
import dagger.Provides
import <%= appPackage %>.di.ForApplication

@Module
class AndroidModule {

    @Provides
    fun provideSharedPreferences(@ForApplication application: Context): SharedPreferences {
        return application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideLayoutInflater(@ForApplication application: Application): LayoutInflater {
        return application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    @Provides
    fun provideInputMethodManager(@ForApplication application: Application): InputMethodManager {
        return application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    fun provideNotificationManager(@ForApplication context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun provideActivityManager(@ForApplication application: Application): ActivityManager {
        return application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }
}
