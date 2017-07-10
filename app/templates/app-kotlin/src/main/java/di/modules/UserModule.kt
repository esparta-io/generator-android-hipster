package <%= appPackage %>.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import <%= appPackage %>.di.UserScope
import <%= appPackage %>.model.OAuth

@Module
class UserModule(private var context: Context, private var oAuth: OAuth) {

    @UserScope
    @Provides
    fun provideOAuth(): OAuth {
        return oAuth
    }
}
