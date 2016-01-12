package <%= appPackage %>.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ActivityScope
