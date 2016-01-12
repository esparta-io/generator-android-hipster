package <%= appPackage %>.di

import javax.inject.Qualifier;

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ForApplication
