package <%= appPackage %>.di.components

import dagger.Subcomponent
import <%= appPackage %>.di.UserScope
import <%= appPackage %>.di.modules.UserModule

// android-hipster-needle-component-injection-import

@UserScope
@Subcomponent(modules = arrayOf(UserModule::class))
interface UserComponent {

  // android-hipster-needle-component-injection-method

}
