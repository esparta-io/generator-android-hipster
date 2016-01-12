package <%= appPackage %>.di.modules

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager

import <%= appPackage %>.di.ForApplication;

import dagger.Module
import dagger.Provides

@Module
public class AndroidModule {

  @Provides
  fun provideSharedPreferences(application: Application): SharedPreferences {
    return application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
  }

  @Provides
  fun provideLayoutInflater(application: Application): LayoutInflater {
    return application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
  }

  @Provides
  fun provideInputMethodManager(application: Application): InputMethodManager {
    return application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  }

  @Provides
  fun provideActivityManager(application: Application): ActivityManager {
    return application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
  }
}
